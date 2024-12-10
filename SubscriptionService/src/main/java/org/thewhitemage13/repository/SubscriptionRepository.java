package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Subscription} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations for {@link Subscription} entities.
 * It also includes custom query methods for retrieving subscriptions based on follower and following IDs, verifying
 * the existence of a subscription, and deleting subscriptions associated with a given user.
 * </p>
 *
 * <h2>Key Operations:</h2>
 * <ul>
 *     <li>Find all subscriptions by follower ID.</li>
 *     <li>Find all subscriptions by following ID.</li>
 *     <li>Check if a subscription exists between a given follower and following user.</li>
 *     <li>Retrieve a subscription by follower ID and following ID.</li>
 *     <li>Count subscriptions by follower or following ID.</li>
 *     <li>Delete all subscriptions for a given follower or following user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Finds all subscriptions where the given {@code followerId} is the follower.
     *
     * @param followerId the ID of the follower
     * @return a list of {@link Subscription} entities where the follower matches the provided {@code followerId}
     */
    List<Subscription> findAllByFollowerId(Long followerId);

    /**
     * Finds all subscriptions where the given {@code followingId} is the user being followed.
     *
     * @param followingId the ID of the user being followed
     * @return a list of {@link Subscription} entities where the following matches the provided {@code followingId}
     */
    List<Subscription> findAllByFollowingId(Long followingId);

    /**
     * Checks if a subscription exists between the given follower and following.
     *
     * @param followerId  the ID of the follower
     * @param followingId the ID of the user being followed
     * @return {@code true} if a subscription exists; {@code false} otherwise
     */
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    /**
     * Finds a subscription based on the follower ID and following ID.
     *
     * @param followerId  the ID of the follower
     * @param followingId the ID of the user being followed
     * @return an {@link Optional} containing the {@link Subscription} if found, or {@link Optional#empty()} if not found
     */
    Optional<Subscription> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    /**
     * Counts the number of subscriptions for a given follower ID.
     *
     * @param followerId the ID of the follower
     * @return the count of subscriptions where the follower matches the provided {@code followerId}
     */
    Long countByFollowerId(Long followerId);

    /**
     * Counts the number of subscriptions for a given following ID.
     *
     * @param followingId the ID of the user being followed
     * @return the count of subscriptions where the following matches the provided {@code followingId}
     */
    Long countByFollowingId(Long followingId);

    /**
     * Deletes all subscriptions associated with the given follower ID.
     *
     * @param followerId the ID of the follower whose subscriptions are to be deleted
     */
    void deleteAllByFollowerId(Long followerId);

    /**
     * Deletes all subscriptions associated with the given following ID.
     *
     * @param followingId the ID of the user being followed whose subscriptions are to be deleted
     */
    void deleteAllByFollowingId(Long followingId);
}
