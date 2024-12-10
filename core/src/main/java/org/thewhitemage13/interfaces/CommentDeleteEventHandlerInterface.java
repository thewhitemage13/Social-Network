package org.thewhitemage13.interfaces;

import org.thewhitemage13.CommentEvent;

/**
 * Interface defining a handler for comment deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a comment is deleted in the system. The method {@code commentDeleted}
 * is invoked with the details of the comment event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to comment deletion events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to comment deletions.</li>
 * </ul>
 *
 * @see CommentEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface CommentDeleteEventHandlerInterface {

    /**
     * Handles the event triggered when a comment is deleted.
     *
     * @param commentEvent the event containing details about the deleted comment
     */
    void commentDeleted(CommentEvent commentEvent);
}
