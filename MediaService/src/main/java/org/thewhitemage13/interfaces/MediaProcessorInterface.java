package org.thewhitemage13.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface for processing media files, including uploading, deleting, and generating URLs for files stored in S3.
 * <p>
 * This interface defines methods for interacting with an S3 storage service. Implementing classes are responsible
 * for handling file uploads, deletions, and generating URLs and file names for stored files.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Uploads files to S3 storage with a specific key.</li>
 *     <li>Deletes files from S3 storage using a key.</li>
 *     <li>Generates a URL to access the file stored in S3.</li>
 *     <li>Generates a file name based on the provided {@link MultipartFile}.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaProcessorInterface {

    /**
     * Uploads a file to S3 storage with a specific key.
     * <p>
     * This method uploads the provided {@link MultipartFile} to an S3 bucket, using the specified key
     * to identify the file in storage.
     * </p>
     *
     * @param key  the unique identifier (key) for the file in S3
     * @param file the {@link MultipartFile} to be uploaded
     * @throws IOException if an I/O error occurs while uploading the file
     */
    void uploadFileToS3(String key, MultipartFile file) throws IOException;

    /**
     * Deletes a file from S3 storage using its unique key.
     * <p>
     * This method removes the file from S3 storage, given its key.
     * </p>
     *
     * @param key the unique identifier (key) of the file to be deleted
     */
    void deleteFileFromS3(String key);

    /**
     * Generates a publicly accessible URL for a file stored in S3.
     * <p>
     * This method constructs a URL to access the file stored in S3 using the given key.
     * </p>
     *
     * @param key the unique identifier (key) for the file in S3
     * @return a URL that can be used to access the file
     */
    String generateS3Url(String key);

    /**
     * Generates a file name for the given {@link MultipartFile}.
     * <p>
     * This method creates a unique file name based on the properties of the provided file,
     * such as its original name or a generated identifier.
     * </p>
     *
     * @param file the {@link MultipartFile} for which to generate the file name
     * @return the generated file name
     */
    String generateFileName(MultipartFile file);
}
