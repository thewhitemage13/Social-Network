package org.thewhitemage13.interfaces;

import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.entity.Subscription;

import java.util.List;


/**
 * Defines the contract for processing subscription data and events.
 * <p>
 * This interface provides methods to convert subscription entities into event objects
 * and Data Access Objects (DAOs), enabling seamless integration between different layers
 * of the application.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Transform subscription entities into {@link SubscriptionEvent} objects for event handling.</li>
 *     <li>Convert lists of {@link Subscription} entities into {@link SubscriptionDAO} objects
 *     for data transfer or storage purposes.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface SubscriptionProcessorInterface {

    /**
     * Converts a {@link Subscription} entity into a {@link SubscriptionEvent}.
     * <p>
     * This method is used to generate a subscription event representation from
     * the provided subscription entity, suitable for event-driven communication.
     * </p>
     *
     * @param subscription the subscription entity to be converted
     * @return a {@link SubscriptionEvent} representing the subscription
     */
    SubscriptionEvent getSubscriptionEvent(Subscription subscription);

    /**
     * Converts a list of {@link Subscription} entities into a list of {@link SubscriptionDAO} objects.
     * <p>
     * This method enables the transformation of subscription data for use in data access or transfer layers.
     * </p>
     *
     * @return a list of {@link SubscriptionDAO} objects representing the subscriptions
     */
    List<SubscriptionDAO> getSubscriptionDAOS(List<Subscription> get);
}
