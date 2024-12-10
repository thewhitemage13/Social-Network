package org.thewhitemage13.interfaces;

import org.thewhitemage13.UserEvent;
import org.thewhitemage13.exception.CommentNotFoundException;
import org.thewhitemage13.exception.MediaNotFoundException;

/**
 * Interface defining a handler for user deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a user is deleted in the system. The method {@code userDeleted}
 * is invoked with the details of the user event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to user deletion events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to user deletion.</li>
 *     <li>Handle exceptions such as {@link MediaNotFoundException} and {@link CommentNotFoundException}
 *         when associated media or comments are not found during the deletion process.</li>
 * </ul>
 *
 * @see UserEvent
 * @see MediaNotFoundException
 * @see CommentNotFoundException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserDeletedEventHandlerInterface {

    /**
     * Handles the event triggered when a user is deleted.
     * <p>
     * This method may throw {@link MediaNotFoundException} if media associated with the user is not found,
     * or {@link CommentNotFoundException} if comments associated with the user are not found.
     * </p>
     *
     * @param userEvent the event containing details about the deleted user
     * @throws MediaNotFoundException if associated media is not found during user deletion
     * @throws CommentNotFoundException if associated comments are not found during user deletion
     */
    void userDeleted(UserEvent userEvent) throws MediaNotFoundException, CommentNotFoundException;
}
