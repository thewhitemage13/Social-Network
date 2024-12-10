package org.thewhitemage13.interfaces;

import org.thewhitemage13.UserEvent;

/**
 * Interface defining a handler for user creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a new user is created in the system. The method {@code userCreated}
 * is invoked with the details of the user event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to user creation events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to user creation.</li>
 * </ul>
 *
 * @see UserEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserCreatedEventHandlerInterface {

    /**
     * Handles the event triggered when a new user is created.
     *
     * @param userEvent the event containing details about the created user
     */
    void userCreated(UserEvent userEvent);
}
