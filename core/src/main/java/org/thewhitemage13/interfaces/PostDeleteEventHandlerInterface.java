package org.thewhitemage13.interfaces;

import org.thewhitemage13.PostEvent;

/**
 * Interface defining a handler for post deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a post is deleted in the system. The method {@code postDelete}
 * is invoked with the details of the post event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to post deletion events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to post deletion.</li>
 * </ul>
 *
 * @see PostEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostDeleteEventHandlerInterface {

    /**
     * Handles the event triggered when a post is deleted.
     *
     * @param postEvent the event containing details about the deleted post
     */
    void postDelete(PostEvent postEvent);
}
