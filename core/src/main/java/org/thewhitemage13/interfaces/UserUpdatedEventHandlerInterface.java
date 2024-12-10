package org.thewhitemage13.interfaces;

import org.thewhitemage13.UserEvent;

/**
 * Interface defining a handler for user update events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a user is updated in the system. The method {@code userUpdated}
 * is invoked with the details of the user event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to user update events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to user updates.</li>
 * </ul>
 *
 * @see UserEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserUpdatedEventHandlerInterface {

    /**
     * Handles the event triggered when a user is updated.
     *
     * @param userEvent the event containing details about the updated user
     */
    void userUpdated(UserEvent userEvent);
}
