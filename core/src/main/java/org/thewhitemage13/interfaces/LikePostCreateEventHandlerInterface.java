package org.thewhitemage13.interfaces;

import org.thewhitemage13.LikeEvent;

/**
 * Interface defining a handler for like post creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a like on a post is created. The method {@code likePostCreate}
 * is invoked with the details of the like event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to like creation events on posts in the system.</li>
 *     <li>Provide custom logic for handling workflows related to like creation on posts.</li>
 * </ul>
 *
 * @see LikeEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikePostCreateEventHandlerInterface {

    /**
     * Handles the event triggered when a like is created on a post.
     *
     * @param likeEvent the event containing details about the like on the post
     */
    void likePostCreate(LikeEvent likeEvent);
}
