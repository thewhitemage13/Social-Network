package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.entity.Media;
import org.thewhitemage13.exceptions.MediaNotFoundException;
import org.thewhitemage13.interfaces.MediaServiceInterface;
import org.thewhitemage13.processor.MediaProcessorImpl;
import org.thewhitemage13.repository.MediaRepository;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link MediaServiceInterface} that handles media file management.
 * <p>
 * This service provides functionality for uploading, deleting, and retrieving media files.
 * It integrates with Amazon S3 for file storage and uses Kafka for event-driven communication.
 * Additionally, it supports caching for media verification and retrieval to optimize performance.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verifies the existence of media files by their URL, with caching support.</li>
 *     <li>Uploads media files to Amazon S3 and saves metadata in the database.</li>
 *     <li>Deletes media files both from S3 and the database.</li>
 *     <li>Retrieves media metadata by ID, with caching support.</li>
 *     <li>Triggers Kafka events for media uploads and deletions.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaServiceInterface {
    private final MediaRepository mediaRepository;
    private final ValidationServiceImpl validationServiceImpl;
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final MediaProcessorImpl mediaProcessorImpl;

    /**
     * Constructs a new {@code MediaServiceImpl} with the specified dependencies.
     * <p>
     * This constructor injects the necessary components such as the {@link MediaRepository},
     * {@link ValidationServiceImpl}, {@link KafkaTemplate}, and {@link MediaProcessorImpl} to
     * perform media-related operations.
     * </p>
     *
     * @param s3Client the {@link S3Client} used for S3 file operations
     * @param mediaRepository the {@link MediaRepository} used for database operations
     * @param validationServiceImpl the {@link ValidationServiceImpl} used for user validation
     * @param kafkaTemplate the {@link KafkaTemplate} used for Kafka messaging
     * @param mediaProcessorImpl the {@link MediaProcessorImpl} used for media file handling
     */
    @Autowired
    public MediaServiceImpl
            (
                    S3Client s3Client,
                    MediaRepository mediaRepository,
                    ValidationServiceImpl validationServiceImpl,
                    KafkaTemplate<Long, Object> kafkaTemplate,
                    MediaProcessorImpl mediaProcessorImpl
            ) {
        this.mediaRepository = mediaRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
        this.mediaProcessorImpl = mediaProcessorImpl;
    }

    /**
     * Verifies whether a media file exists by its URL.
     * <p>
     * This method checks the database to see if a media entry with the specified URL already exists.
     * The result is cached to improve performance, and caching is skipped if the result is {@code false}.
     * </p>
     *
     * @param url the URL of the media to verify
     * @return {@code true} if the media exists, {@code false} otherwise
     */
    @Override
    @Cacheable(value = "mediaVerificationCache", key = "#url", unless = "#result == false")
    public boolean mediaVerification(String url) {
        return mediaRepository.existsByUrl(url);
    }

    /**
     * Uploads a media file for a specific user.
     * <p>
     * This method validates the user, generates a unique file name, uploads the file to S3,
     * and stores its metadata in the database. It also triggers a Kafka event to notify other services
     * about the new media upload.
     * </p>
     *
     * @param userId the ID of the user uploading the media
     * @param file the media file to upload
     * @return the URL of the uploaded media
     * @throws IOException if an error occurs while uploading the file
     */
    @Override
    public String uploadMedia(Long userId, MultipartFile file) throws IOException {
        validationServiceImpl.validateUser(userId);

        Media media = new Media();
        String fileName = mediaProcessorImpl.generateFileName(file);
        String key = "media/" + fileName;

        mediaProcessorImpl.uploadFileToS3(key, file);

        String url = mediaProcessorImpl.generateS3Url(key);
        media.setUrl(url);

        media.setUserId(userId);
        media.setFileName(fileName);
        media.setFileSize((double) file.getSize());
        media.setFileType(file.getContentType());
        media.setUploadDate(LocalDateTime.now());

        mediaRepository.save(media);

        MediaEvent mediaEvent = new MediaEvent
                (
                        media.getMediaId(),
                        media.getUserId(),
                        media.getUrl(),
                        media.getFileName(),
                        media.getFileSize(),
                        media.getFileType(),
                        media.getUploadDate()
                );

//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("media.upload", media.getMediaId(), mediaEvent);
//            return null;
//        });

        kafkaTemplate.send("media.upload", media.getMediaId(), mediaEvent);
        return url;
    }

    /**
     * Deletes all media files associated with a specific user.
     * <p>
     * This method retrieves all media entries for the given user ID and deletes them from the database
     * as well as from S3. It also triggers Kafka events for each deletion.
     * </p>
     *
     * @param userId the ID of the user whose media files should be deleted
     * @throws MediaNotFoundException if no media is found for the specified user
     */
    @Caching(evict = {
            @CacheEvict(value = "mediaCache", key = "#id"),
            @CacheEvict(value = "mediaVerificationCache", allEntries = true)
    })
    public void deleteAllByUserId(Long userId) throws MediaNotFoundException {
        List<Media> mediaList = mediaRepository.findAllByUserId(userId);
        for (Media media : mediaList) {
            deleteMedia(media.getMediaId());
        }
    }

    /**
     * Deletes a specific media file by its ID.
     * <p>
     * This method removes the media entry from the database and deletes the corresponding file
     * from S3. It also triggers a Kafka event to notify other services about the media deletion.
     * </p>
     *
     * @param id the ID of the media to delete
     * @throws MediaNotFoundException if no media is found for the specified ID
     */
    @Override
    public void deleteMedia(Long id) throws MediaNotFoundException {
        Media media = mediaRepository.findById(id).orElseThrow(() -> new MediaNotFoundException("Media with id = %s not found".formatted(id)));
        String key = "media/" + media.getFileName();
        mediaProcessorImpl.deleteFileFromS3(key);
        mediaRepository.deleteById(id);
        MediaEvent mediaEvent = new MediaEvent
                (
                        media.getMediaId(),
                        media.getUserId(),
                        media.getUrl(),
                        media.getFileName(),
                        media.getFileSize(),
                        media.getFileType(),
                        media.getUploadDate()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("media.deleted", media.getMediaId(), mediaEvent);
//            return null;
//        });

        kafkaTemplate.send("media.deleted", media.getMediaId(), mediaEvent);
    }

    /**
     * Retrieves media metadata by its ID.
     * <p>
     * This method fetches the media information from the database. The result is cached to improve performance.
     * </p>
     *
     * @param id the ID of the media to retrieve
     * @return the {@link Media} entity corresponding to the specified ID
     * @throws MediaNotFoundException if no media is found for the specified ID
     */
    @Override
    @Cacheable(value = "mediaCache", key = "#id")
    public Media getMedia(Long id) throws MediaNotFoundException {
        return mediaRepository.findById(id).orElseThrow(() -> new MediaNotFoundException("Media with id = %s not found".formatted(id)));
    }
}
