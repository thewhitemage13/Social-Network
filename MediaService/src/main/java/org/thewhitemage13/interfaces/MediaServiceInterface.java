package org.thewhitemage13.interfaces;

import org.springframework.web.multipart.MultipartFile;
import org.thewhitemage13.entity.Media;
import org.thewhitemage13.exceptions.MediaNotFoundException;

import java.io.IOException;


/**
 * Interface for managing media files in the system.
 * <p>
 * This interface defines the core operations for media management, including verification,
 * uploading, deletion, and retrieval of media. Implementing classes are responsible for
 * handling the logic related to storing, deleting, and accessing media.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verifies the validity of a media URL.</li>
 *     <li>Handles the upload of media files for a specific user.</li>
 *     <li>Deletes a media file based on its ID.</li>
 *     <li>Retrieves a media file by its ID.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaServiceInterface {

    /**
     * Verifies the validity of a media URL.
     * <p>
     * This method checks if the given URL corresponds to a valid media file in the system.
     * </p>
     *
     * @param url the URL of the media to be verified
     * @return {@code true} if the URL is valid; {@code false} otherwise
     */
    boolean mediaVerification(String url);

    /**
     * Uploads a media file for a specific user.
     * <p>
     * This method handles the upload process for a media file, associates it with the
     * provided user ID, and stores it in the system. The method returns the URL of the uploaded file.
     * </p>
     *
     * @param userId the ID of the user uploading the media
     * @param file the media file to be uploaded
     * @return the URL of the uploaded media
     * @throws IOException if an error occurs during the file upload
     */
    String uploadMedia(Long userId, MultipartFile file) throws IOException;


    /**
     * Deletes a media file by its ID.
     * <p>
     * This method deletes the media file associated with the given ID from the system.
     * If the media is not found, a {@link MediaNotFoundException} will be thrown.
     * </p>
     *
     * @param id the ID of the media file to be deleted
     * @throws MediaNotFoundException if the media file with the given ID does not exist
     */
    void deleteMedia(Long id) throws MediaNotFoundException;

    /**
     * Retrieves a media file by its ID.
     * <p>
     * This method returns the media file associated with the given ID.
     * If the media is not found, a {@link MediaNotFoundException} will be thrown.
     * </p>
     *
     * @param id the ID of the media file to be retrieved
     * @return the media file with the specified ID
     * @throws MediaNotFoundException if the media file with the given ID does not exist
     */
    Media getMedia(Long id) throws MediaNotFoundException;
}
