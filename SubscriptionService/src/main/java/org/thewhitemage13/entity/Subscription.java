package org.thewhitemage13.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a subscription entity in the system.
 * <p>
 * This class is a JPA entity that maps to the corresponding database table.
 * It captures the relationship between a follower and a user they are following,
 * including the timestamp when the subscription was created.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identification for each subscription via {@code subscriptionId}.</li>
 *     <li>Tracks the follower and the user being followed through their respective IDs.</li>
 *     <li>Stores the timestamp indicating when the subscription was created.</li>
 * </ul>
 *
 * <p>
 * Lombok annotations are used to reduce boilerplate code for getters, setters,
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
@Entity
public class Subscription {

    /**
     * Unique identifier for the subscription.
     * <p>
     * This field serves as the primary key in the database table and is
     * automatically generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    /**
     * The unique identifier of the follower.
     * <p>
     * Represents the ID of the user who is following another user in the system.
     * </p>
     */
    private Long followerId;

    /**
     * The unique identifier of the user being followed.
     * <p>
     * Represents the ID of the user who is being followed by another user in the system.
     * </p>
     */
    private Long followingId;

    /**
     * The timestamp when the subscription was created.
     * <p>
     * Indicates the date and time when the follower started following the user.
     * </p>
     */
    private LocalDateTime createdAt;
}
