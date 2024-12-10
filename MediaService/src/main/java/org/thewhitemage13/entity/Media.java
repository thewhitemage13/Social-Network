package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a media entity in the system.
 * <p>
 * This class maps to the "media" table in the database, storing information about uploaded media files.
 * It includes details such as the unique media ID, user ID of the uploader, file URL, file name, size, type,
 * and the date and time the file was uploaded.
 * </p>
 *
 * Annotations from Lombok are used to generate constructors, getters, and setters automatically,
 * reducing boilerplate code.
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique media identification with an auto-generated ID.</li>
 *     <li>Storage of user ID to link the media to the uploader.</li>
 *     <li>Information about the media file such as URL, name, size, and type.</li>
 *     <li>Timestamp of when the media file was uploaded.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media")
@Entity
public class Media {

    /**
     * Unique identifier for the media file.
     * <p>
     * This field is the primary key in the "media" table and is automatically
     * generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    /**
     * Identifier of the user who uploaded the media.
     * <p>
     * Maps to the "user_id" column in the "media" table. This serves as a
     * foreign key to link the media to the user who uploaded it.
     * </p>
     */
    private Long userId;

    /**
     * The URL where the media file is stored.
     * <p>
     * This field contains the path or link to the media file on the server or cloud storage.
     * </p>
     */
    private String url;

    /**
     * The name of the media file.
     * <p>
     * This field contains the original file name of the uploaded media, which can include
     * the file extension (e.g., ".jpg", ".mp4").
     * </p>
     */
    private String fileName;

    /**
     * The size of the media file in bytes.
     * <p>
     * This field represents the size of the media file, useful for file management and storage optimization.
     * </p>
     */
    private Double fileSize;

    /**
     * The type of the media file (e.g., image, video, audio).
     * <p>
     * This field indicates the type of media file, such as "image/jpeg", "video/mp4", etc.
     * </p>
     */
    private String fileType;

    /**
     * The date and time when the media was uploaded.
     * <p>
     * This field stores the timestamp of when the media file was uploaded to the system.
     * </p>
     */
    private LocalDateTime uploadDate;
}
