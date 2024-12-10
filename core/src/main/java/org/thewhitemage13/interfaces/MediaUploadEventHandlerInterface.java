package org.thewhitemage13.interfaces;

import org.thewhitemage13.MediaEvent;

/**
 * Interface defining a handler for media upload events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when media is uploaded in the system. The method {@code mediaUpload}
 * is invoked with the details of the media event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to media upload events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to media uploads.</li>
 * </ul>
 *
 * @see MediaEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaUploadEventHandlerInterface {

    /**
     * Handles the event triggered when media is uploaded.
     *
     * @param mediaEvent the event containing details about the uploaded media
     */
    void mediaUpload(MediaEvent mediaEvent);
}
