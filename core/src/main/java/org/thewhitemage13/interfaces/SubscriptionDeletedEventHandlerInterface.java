package org.thewhitemage13.interfaces;

import org.thewhitemage13.SubscriptionEvent;

/**
 * Interface defining a handler for subscription deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a subscription is deleted in the system. The method {@code subscriptionDeleted}
 * is invoked with the details of the subscription event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to subscription deletion events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to subscription deletion.</li>
 * </ul>
 *
 * @see SubscriptionEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface SubscriptionDeletedEventHandlerInterface {

    /**
     * Handles the event triggered when a subscription is deleted.
     *
     * @param subscriptionEvent the event containing details about the deleted subscription
     */
    void subscriptionCreated(SubscriptionEvent subscriptionEvent);
}
