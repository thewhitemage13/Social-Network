package org.thewhitemage13;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event related to media (e.g., images, videos, etc.) in the system.
 * <p>
 * This class encapsulates details about media uploads, including the media's unique ID,
 * the user who uploaded it, the file's metadata such as URL, file name, file size, and
 * file type, along with the timestamp when the media was uploaded.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier of the media.</li>
 *     <li>Associates the media with the user who uploaded it.</li>
 *     <li>Stores the URL, file name, file size, and type of the media.</li>
 *     <li>Records the timestamp when the media was uploaded.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class MediaEvent implements Serializable {
    private Long mediaId;
    private Long userId;
    private String url;
    private String fileName;
    private Double fileSize;
    private String fileType;
    private LocalDateTime uploadDate;

    /**
     * Default constructor for MediaEvent.
     */
    public MediaEvent() {
    }

    /**
     * Constructs a new {@code MediaEvent} with the specified parameters.
     *
     * @param mediaId the unique identifier for the media
     * @param userId the ID of the user who uploaded the media
     * @param url the URL of the media
     * @param fileName the file name of the media
     * @param fileSize the size of the media file
     * @param fileType the type of the media file (e.g., image/jpeg)
     * @param uploadDate the timestamp when the media was uploaded
     */
    public MediaEvent(Long mediaId, Long userId, String url, String fileName, Double fileSize, String fileType, LocalDateTime uploadDate) {
        this.mediaId = mediaId;
        this.userId = userId;
        this.url = url;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
    }

    /**
     * Gets the unique identifier for the media.
     *
     * @return the media ID
     */
    public Long getMediaId() {
        return mediaId;
    }

    /**
     * Sets the unique identifier for the media.
     *
     * @param mediaId the media ID to set
     */
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * Gets the ID of the user who uploaded the media.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who uploaded the media.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the URL of the media.
     *
     * @return the media URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the media.
     *
     * @param url the media URL to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the file name of the media.
     *
     * @return the media file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name of the media.
     *
     * @param fileName the media file name to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the size of the media file.
     *
     * @return the media file size
     */
    public Double getFileSize() {
        return fileSize;
    }

    /**
     * Sets the size of the media file.
     *
     * @param fileSize the media file size to set
     */
    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the type of the media file.
     *
     * @return the media file type (e.g., image/jpeg)
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the type of the media file.
     *
     * @param fileType the media file type to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Gets the timestamp when the media was uploaded.
     *
     * @return the upload timestamp
     */
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    /**
     * Sets the timestamp when the media was uploaded.
     *
     * @param uploadDate the upload timestamp to set
     */
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Returns a string representation of the MediaEvent.
     * <p>
     * The string includes the media's ID, user ID, URL, file name, file size, file type,
     * and upload timestamp.
     * </p>
     *
     * @return a string representation of the media event
     */
    @Override
    public String toString() {
        return "MediaEvent{" +
                "mediaId=" + mediaId +
                ", userId=" + userId +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
