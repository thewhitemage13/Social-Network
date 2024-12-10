package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.exceotion.SubscriptionNotFoundException;
import org.thewhitemage13.service.SubscriptionServiceImpl;

import java.util.List;

@Tag(name = "Subscription Controller", description = "Handles subscription-related operations for followers and following management.")
@RestController
@RequestMapping("/users")
public class SubscriptionController {
    private final SubscriptionServiceImpl subscriptionServiceImpl;

    @Autowired
    public SubscriptionController(SubscriptionServiceImpl subscriptionServiceImpl) {
        this.subscriptionServiceImpl = subscriptionServiceImpl;
    }

    @Operation(summary = "Get user followers", description = "Retrieve a list of users who follow the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserSubscriptionDTO>> getUserFollowers(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(subscriptionServiceImpl.getFollowers(userId));
    }

    @Operation(summary = "Get user following", description = "Retrieve a list of users that the specified user is following.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following list retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserSubscriptionDTO>> getUserFollowing(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(subscriptionServiceImpl.getFollowing(userId));
    }

    @Operation(summary = "Count followers", description = "Get the count of users following the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<Long> countFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionServiceImpl.countFollowingByFollower(userId));
    }

    @Operation(summary = "Count following", description = "Get the count of users that the specified user is following.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/following/count")
    public ResponseEntity<Long> countFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionServiceImpl.countFollowersByFollowingId(userId));
    }

    @Operation(summary = "Check if user is following another user", description = "Verify if one user is following another user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Follow status retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{followerId}/is-following/{followingId}")
    public ResponseEntity<Boolean> isUserFollowing(@PathVariable Long followerId, @PathVariable Long followingId) {
        try {
            boolean isFollowing = subscriptionServiceImpl.followingVerification(followerId, followingId);
            return ResponseEntity.ok(isFollowing);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(summary = "Get following by user", description = "Retrieve all following subscriptions for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following subscriptions retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-following-by-followingId/{followingId}")
    public ResponseEntity<List<SubscriptionDAO>> getAllFollowingByFollowingId(@PathVariable("followingId") Long followingId) {
        try {
            return ResponseEntity.ok(subscriptionServiceImpl.getAllFollowingByFollowingId(followingId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get following by follower", description = "Retrieve all following subscriptions for a specific follower.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following subscriptions retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-following-by-follower/{followerId}")
    public ResponseEntity<List<SubscriptionDAO>> getAllFollowingByFollowerId(@PathVariable("followerId") Long followerId) {
        try {
            return ResponseEntity.ok(subscriptionServiceImpl.getAllFollowingByFollowerId(followerId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Create subscription", description = "Subscribe one user to another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{followerId}/subscribe/{followingId}")
    public ResponseEntity<String> createSubscription(@PathVariable Long followerId, @PathVariable Long followingId) {
        try {
            subscriptionServiceImpl.createSubscription(followerId, followingId);
            return ResponseEntity.ok("Subscription created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete subscription", description = "Unsubscribe one user from another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{followerId}/unsubscribe/{followingId}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Long followerId, @PathVariable Long followingId) {
        try {
            subscriptionServiceImpl.deleteSubscription(followerId, followingId);
            return ResponseEntity.ok("Subscription deleted");
        } catch (SubscriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription with followerId = %s and followingId = %s not found".formatted(followerId, followingId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
