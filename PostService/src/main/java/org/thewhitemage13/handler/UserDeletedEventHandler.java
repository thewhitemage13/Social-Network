package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.service.PostServiceImpl;

/**
 * Handles events related to a user being deleted.
 * <p>
 * This class listens for {@link UserEvent} messages from the Kafka topic "user.deleted"
 * and processes them by deleting all posts associated with the deleted user.
 * The class interacts with the {@link PostServiceImpl} to remove posts from the system.
 * </p>
 * <p>
 * The handler is marked as a {@link KafkaListener}, which means it will listen for
 * events on the specified Kafka topic and invoke the appropriate method when a
 * {@link UserEvent} is received.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandler {
    private final PostServiceImpl postServiceImpl;

    /**
     * Constructs a new {@code UserDeletedEventHandler} with the specified {@link PostServiceImpl}.
     * <p>
     * The constructor is annotated with {@code @Autowired}, which allows Spring to inject
     * the {@code PostServiceImpl} dependency at runtime.
     * </p>
     *
     * @param postServiceImpl the service that manages posts, used to delete posts associated with a user
     */
    @Autowired
    public UserDeletedEventHandler(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    /**
     * Handles the event when a user is deleted.
     * <p>
     * This method is invoked when a {@link UserEvent} is received from the Kafka topic
     * "user.deleted". It delegates the task of deleting all posts associated with the
     * deleted user to the {@link PostServiceImpl}.
     * </p>
     *
     * @param userEvent the event containing the user's information, including the user ID
     * @throws PostNotFoundException if an error occurs while attempting to delete posts associated with the user
     */
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) throws PostNotFoundException {
        postServiceImpl.deleteAllByUserId(userEvent.getUserId());
    }
}
