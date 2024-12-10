package org.thewhitemage13.interfaces;

import org.thewhitemage13.MediaEvent;

/**
 * Interface defining a handler for media deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when media is deleted in the system. The method {@code mediaDelete}
 * is invoked with the details of the media event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to media deletion events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to media deletion.</li>
 * </ul>
 *
 * @see MediaEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaDeleteEventHandlerInterface {

    /**
     * Handles the event triggered when media is deleted.
     *
     * @param mediaEvent the event containing details about the deleted media
     */
    void mediaDelete(MediaEvent mediaEvent);
}
