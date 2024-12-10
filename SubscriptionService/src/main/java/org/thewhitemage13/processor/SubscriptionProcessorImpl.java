package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.entity.Subscription;
import org.thewhitemage13.interfaces.SubscriptionProcessorInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link SubscriptionProcessorInterface} for processing subscription data.
 * <p>
 * This component provides functionality for transforming subscription entities into event objects
 * and Data Access Objects (DAOs). The implementation is responsible for mapping the subscription data
 * to different representations used in various parts of the application.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Convert a {@link Subscription} entity to a {@link SubscriptionEvent} for event handling.</li>
 *     <li>Convert a list of {@link Subscription} entities into a list of {@link SubscriptionDAO} objects for data transfer.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class SubscriptionProcessorImpl implements SubscriptionProcessorInterface {

    /**
     * Converts a {@link Subscription} entity into a {@link SubscriptionEvent}.
     * <p>
     * This method is used to create a {@link SubscriptionEvent} that represents a subscription,
     * making it suitable for event-driven communication and processing.
     * </p>
     *
     * @param subscription the {@link Subscription} entity to be converted
     * @return a {@link SubscriptionEvent} representing the provided subscription
     */
    @Override
    public SubscriptionEvent getSubscriptionEvent(Subscription subscription) {
        return new SubscriptionEvent
                (
                        subscription.getSubscriptionId(),
                        subscription.getFollowerId(),
                        subscription.getFollowingId(),
                        subscription.getCreatedAt()
                );
    }

    /**
     * Converts a list of {@link Subscription} entities into a list of {@link SubscriptionDAO} objects.
     * <p>
     * This method enables the transformation of a list of {@link Subscription} entities into a list of
     * {@link SubscriptionDAO} objects for data transfer or storage purposes.
     * </p>
     *
     * @return a list of {@link SubscriptionDAO} objects representing the provided subscriptions
     */
    @Override
    public List<SubscriptionDAO> getSubscriptionDAOS(List<Subscription> get) {
        List<SubscriptionDAO> subscriptions = new ArrayList<>();
        for (Subscription subscription : get) {
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
            subscriptionDAO.setFollowingId(subscription.getFollowingId());
            subscriptionDAO.setFollowerId(subscription.getFollowerId());
            subscriptions.add(subscriptionDAO);
        }
        return subscriptions;
    }
}
