package org.thewhitemage13.interfaces;

import org.thewhitemage13.PostEvent;

/**
 * Interface defining a handler for post update events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a post is updated in the system. The method {@code updatedPost}
 * is invoked with the details of the post event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to post update events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to post updates.</li>
 * </ul>
 *
 * @see PostEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostUpdatedEventHandlerInterface {

    /**
     * Handles the event triggered when a post is updated.
     *
     * @param postEvent the event containing details about the updated post
     */
    void updatedPost(PostEvent postEvent);
}
