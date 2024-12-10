package org.thewhitemage13.interfaces;

import org.thewhitemage13.PostEvent;

/**
 * Interface defining a handler for post creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a new post is created in the system. The method {@code postCreate}
 * is invoked with the details of the post event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to post creation events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to post creation.</li>
 * </ul>
 *
 * @see PostEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostCreateEventHandlerInterface {

    /**
     * Handles the event triggered when a new post is created.
     *
     * @param postEvent the event containing details about the created post
     */
    void postCreate(PostEvent postEvent);
}
