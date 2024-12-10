package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.MediaClient;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.exceptions.MediaNotFoundException;
import org.thewhitemage13.interfaces.MediaValidationServiceInterface;

import java.util.Collections;
import java.util.List;

/**
 * Service implementation for validating media resources associated with users.
 * <p>
 * This service interacts with external media and post services to verify the existence of media
 * and to retrieve media URLs related to a specific user.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Validating the existence of a user's profile picture.</li>
 *     <li>Fetching media URLs linked to a user's posts.</li>
 *     <li>Handling exceptions gracefully and providing fallback results.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link MediaClient} - Used to verify media existence.</li>
 *     <li>{@link PostClient} - Used to retrieve media URLs associated with a user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class MediaValidationServiceImpl implements MediaValidationServiceInterface {
    private final MediaClient mediaClient;
    private final PostClient postClient;

    /**
     * Constructs an instance of {@code MediaValidationServiceImpl}.
     *
     * @param mediaClient the client for interacting with the media service
     * @param postClient  the client for interacting with the post service
     */
    @Autowired
    public MediaValidationServiceImpl(MediaClient mediaClient, PostClient postClient) {
        this.mediaClient = mediaClient;
        this.postClient = postClient;
    }

    /**
     * Retrieves media URLs associated with the user's posts.
     * <p>
     * Attempts to fetch media URLs from the {@link PostClient}. If the request fails,
     * an empty list is returned.
     * </p>
     *
     * @param userId the ID of the user whose media URLs are being retrieved
     * @return a list of media URLs; an empty list if the client call fails
     */
    @Override
    public List<String> validateMedia(Long userId) {
        List<String> mediaPostsUrl;
        try {
            mediaPostsUrl = postClient.getMediaUrlByUserId(userId).getBody();
        } catch (Exception e) {
            mediaPostsUrl = Collections.emptyList();
        }
        return mediaPostsUrl;
    }

    /**
     * Validates the profile picture URL provided in the {@link CreateUserDTO}.
     * <p>
     * Calls the {@link MediaClient} to check if the media resource exists. Throws
     * a {@link MediaNotFoundException} if the media resource is not found.
     * </p>
     *
     * @param createUserDTO the DTO containing the profile picture URL to validate
     * @throws MediaNotFoundException if the profile picture does not exist
     */
    @Override
    public void validatePicture(CreateUserDTO createUserDTO) {
        if (createUserDTO.getProfilePictureUrl() != null) {
            ResponseEntity<Boolean> isCrate = mediaClient.mediaVerification(createUserDTO.getProfilePictureUrl());
            Boolean result = isCrate.getBody();
            if (Boolean.FALSE.equals(result)) {
                throw new MediaNotFoundException("The profile picture with url = %s not found".formatted(createUserDTO.getProfilePictureUrl()));
            }
        }
    }
}
