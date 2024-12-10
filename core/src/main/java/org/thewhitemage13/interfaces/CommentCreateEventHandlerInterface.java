package org.thewhitemage13.interfaces;

import org.thewhitemage13.CommentEvent;

/**
 * Interface defining a handler for comment creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a new comment is created in the system. The method {@code commentCreated}
 * is invoked with the details of the comment event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to comment creation events in the system.</li>
 *     <li>Provide custom logic for handling comment-related workflows.</li>
 * </ul>
 *
 * @see CommentEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface CommentCreateEventHandlerInterface {

    /**
     * Handles the event triggered when a new comment is created.
     *
     * @param commentEvent the event containing details about the created comment
     */
    void commentCreated(CommentEvent commentEvent);
}
