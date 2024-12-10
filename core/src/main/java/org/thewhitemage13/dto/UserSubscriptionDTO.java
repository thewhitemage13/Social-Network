package org.thewhitemage13.dto;


/**
 * Data Transfer Object (DTO) representing a user's subscription details.
 * <p>
 * This class encapsulates basic information about a user's subscription,
 * including their unique identifier and username. It is primarily used to
 * transfer subscription data between different layers of the application.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Encapsulation of user subscription details: {@code userId} and {@code username}.</li>
 *     <li>Provides constructors for creating and initializing instances.</li>
 *     <li>Implements a custom {@code toString} method for easy debugging and logging.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UserSubscriptionDTO {

    /**
     * Unique identifier of the user.
     * <p>
     * Represents the user's ID in the subscription system.
     * </p>
     */
    private Long userId;

    /**
     * Username associated with the user's subscription.
     * <p>
     * This field stores the name that identifies the user within the system.
     * </p>
     */
    private String username;

    /**
     * Default constructor for creating an empty {@code UserSubscriptionDTO}.
     * <p>
     * Primarily used for frameworks or libraries that require a no-argument constructor.
     * </p>
     */
    public UserSubscriptionDTO() {
    }

    /**
     * Constructs a new {@code UserSubscriptionDTO} with the specified user ID and username.
     *
     * @param userId   the unique identifier of the user
     * @param username the username associated with the user's subscription
     */
    public UserSubscriptionDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the username associated with the user's subscription.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username associated with the user's subscription.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns a string representation of this {@code UserSubscriptionDTO}.
     * <p>
     * The string includes the {@code userId} and {@code username} fields.
     * </p>
     *
     * @return a string representation of the user subscription details
     */
    @Override
    public String toString() {
        return "UserSubscriptionDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
