package org.thewhitemage13.interfaces;

import org.thewhitemage13.LikeEvent;

/**
 * Interface defining a handler for like comment creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a like on a comment is created. The method {@code likeCommentCreated}
 * is invoked with the details of the like event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to like creation events on comments in the system.</li>
 *     <li>Provide custom logic for handling workflows related to like creation on comments.</li>
 * </ul>
 *
 * @see LikeEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeCommentCreateHandlerInterface {

    /**
     * Handles the event triggered when a like is created on a comment.
     *
     * @param likeEvent the event containing details about the like on the comment
     */
    void likeCommentCreated(LikeEvent likeEvent);
}
