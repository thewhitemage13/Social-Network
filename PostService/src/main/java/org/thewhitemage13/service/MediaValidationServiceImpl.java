package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.MediaClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.exception.MediaNotFoundException;
import org.thewhitemage13.interfaces.MediaValidationServiceInterface;

/**
 * Service implementation for validating media related to posts.
 * <p>
 * This service communicates with an external media service via the {@link MediaClient}
 * to verify the validity of media URLs. It ensures that the media associated with a
 * post is valid before allowing further processing. If the media is not found,
 * an exception is thrown.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class MediaValidationServiceImpl implements MediaValidationServiceInterface {
    private final MediaClient mediaClient;

    /**
     * Constructs a new {@code MediaValidationServiceImpl} with the specified {@link MediaClient}.
     * <p>
     * The constructor injects the {@link MediaClient}, which is used to verify the validity
     * of media URLs by interacting with the external media service.
     * </p>
     *
     * @param mediaClient the {@link MediaClient} used to interact with the media service
     */
    @Autowired
    public MediaValidationServiceImpl(MediaClient mediaClient) {
        this.mediaClient = mediaClient;
    }

    /**
     * Verifies if the media URL provided in the {@link CreatePostDTO} is valid.
     * <p>
     * This method calls the external media service via the {@link MediaClient} to
     * check if the media URL is valid. If the media is not found (i.e., the external
     * service returns {@code false}), a {@link MediaNotFoundException} is thrown.
     * </p>
     *
     * @param createPostDTO the {@link CreatePostDTO} containing the media URL to be verified
     * @throws MediaNotFoundException if the media URL is not found by the external media service
     */
    @Override
    public void isCreateMedia(CreatePostDTO createPostDTO){
        Boolean status;

        ResponseEntity<Boolean> isCrateMedia = mediaClient.mediaVerification(createPostDTO.getMediaUrl());
        status = isCrateMedia.getBody();

        if (Boolean.FALSE.equals(status)) {
            throw new MediaNotFoundException("Media with url = %s not found".formatted(createPostDTO.getMediaUrl()));
        }
    }
}
