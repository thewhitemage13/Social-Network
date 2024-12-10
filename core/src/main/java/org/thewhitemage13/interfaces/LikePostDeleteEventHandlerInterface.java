package org.thewhitemage13.interfaces;

import org.thewhitemage13.LikeEvent;

/**
 * Interface defining a handler for like post deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a like on a post is deleted. The method {@code likePostDeleted}
 * is invoked with the details of the like event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to like deletion events on posts in the system.</li>
 *     <li>Provide custom logic for handling workflows related to like deletion on posts.</li>
 * </ul>
 *
 * @see LikeEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikePostDeleteEventHandlerInterface {

    /**
     * Handles the event triggered when a like is deleted from a post.
     *
     * @param likeEvent the event containing details about the deleted like on the post
     */
    void likePostDeleted(LikeEvent likeEvent);
}
