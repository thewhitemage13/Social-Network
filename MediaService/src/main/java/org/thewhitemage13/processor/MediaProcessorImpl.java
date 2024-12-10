package org.thewhitemage13.processor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thewhitemage13.interfaces.MediaProcessorInterface;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

/**
 * Implementation of the {@link MediaProcessorInterface} for managing media files on Amazon S3.
 * <p>
 * This class provides methods for uploading, deleting, and generating URLs for media files stored in an S3 bucket.
 * It also handles generating unique file names for uploaded files using UUIDs.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Uploads files to Amazon S3 with a specified key.</li>
 *     <li>Deletes files from Amazon S3 using a unique key.</li>
 *     <li>Generates a publicly accessible URL for files stored in S3.</li>
 *     <li>Generates a unique file name based on a UUID for the given {@link MultipartFile}.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class MediaProcessorImpl implements MediaProcessorInterface {
    private final S3Client amazonS3;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    /**
     * Constructs a new {@code MediaProcessorImpl} with the specified {@link S3Client}.
     * <p>
     * The constructor injects the {@code S3Client} which is used for interacting with the Amazon S3 service.
     * </p>
     *
     * @param amazonS3 the {@link S3Client} used to interact with Amazon S3
     */
    public MediaProcessorImpl(S3Client amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Uploads a file to Amazon S3 using the specified key.
     * <p>
     * This method uploads the provided {@link MultipartFile} to the configured S3 bucket,
     * using the specified key to uniquely identify the file.
     * </p>
     *
     * @param key  the unique key for the file in the S3 bucket
     * @param file the {@link MultipartFile} to be uploaded
     * @throws IOException if an error occurs while uploading the file
     */
    @Override
    public void uploadFileToS3(String key, MultipartFile file) throws IOException {
        amazonS3.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    /**
     * Deletes a file from Amazon S3 using the specified key.
     * <p>
     * This method removes the file from the S3 bucket corresponding to the provided key.
     * </p>
     *
     * @param key the unique key of the file to be deleted
     */
    @Override
    public void deleteFileFromS3(String key) {
        amazonS3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
    }

    /**
     * Generates a publicly accessible URL for a file stored in Amazon S3.
     * <p>
     * This method constructs a URL to access the file stored in the configured S3 bucket using the specified key.
     * </p>
     *
     * @param key the unique key of the file stored in S3
     * @return a URL that can be used to access the file
     */
    @Override
    public String generateS3Url(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    /**
     * Generates a unique file name based on a UUID and the original file name.
     * <p>
     * This method creates a file name by combining a randomly generated UUID with the original file name,
     * ensuring that the file name is unique.
     * </p>
     *
     * @param file the {@link MultipartFile} for which to generate a file name
     * @return a unique file name based on the UUID and the original file name
     */
    @Override
    public String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }
}
