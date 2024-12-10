package org.thewhitemage13.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a subscription data access object.
 * <p>
 * This class models the relationship between a follower and the user being followed.
 * It contains the identifiers for both the follower and the user being followed.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Identification of the follower via {@code followerId}.</li>
 *     <li>Identification of the user being followed via {@code followingId}.</li>
 * </ul>
 *
 * <p>
 * Lombok annotations are used to generate boilerplate code such as getters, setters,
 * and constructors.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDAO {

    /**
     * The unique identifier of the follower.
     * <p>
     * This field represents the ID of the user who is following another user.
     * </p>
     */
    private Long followerId;

    /**
     * The unique identifier of the user being followed.
     * <p>
     * This field represents the ID of the user who is being followed by another user.
     * </p>
     */
    private Long followingId;
}
