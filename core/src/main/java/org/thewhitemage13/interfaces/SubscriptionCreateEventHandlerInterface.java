package org.thewhitemage13.interfaces;

import org.thewhitemage13.SubscriptionEvent;

/**
 * Interface defining a handler for subscription creation events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a subscription is created in the system. The method {@code subscriptionCreated}
 * is invoked with the details of the subscription event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to subscription creation events in the system.</li>
 *     <li>Provide custom logic for handling workflows related to subscription creation.</li>
 * </ul>
 *
 * @see SubscriptionEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface SubscriptionCreateEventHandlerInterface {

    /**
     * Handles the event triggered when a subscription is created.
     *
     * @param subscriptionEvent the event containing details about the created subscription
     */
    void subscriptionCreated(SubscriptionEvent subscriptionEvent);
}
