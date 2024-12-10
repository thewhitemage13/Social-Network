package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.exceptions.LikeNotFoundException;
import org.thewhitemage13.service.LikeServiceImpl;

@Tag(name = "Like Controller", description = "Operations related to likes management for posts and comments")
@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    public LikeController(LikeServiceImpl likeServiceImpl) {
        this.likeServiceImpl = likeServiceImpl;
    }

    @Operation(summary = "Get like count for a post", description = "Retrieves the total number of likes for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/posts/{postId}/count")
    public ResponseEntity<Long> getPostLikeCount(@PathVariable("postId") Long postId) {
        try {
            return ResponseEntity.ok(likeServiceImpl.getPostLikeCount(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Like a post", description = "Registers a like for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post like successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/posts/like")
    public ResponseEntity<String> postLike(@RequestBody CreateLikePost createLikePost) {
        try {
            likeServiceImpl.postLike(createLikePost);
            return ResponseEntity.ok("Post like successful");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Like a comment", description = "Registers a like for a specific comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment like successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/comments/like")
    public ResponseEntity<String> commentLike(@RequestBody CreateLikeComment createLikeComment) {
        try {
            likeServiceImpl.commentLike(createLikeComment);
            return ResponseEntity.ok("Post like successful");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a like", description = "Deletes a like by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Like not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable("likeId") Long likeId) {
        try {
            likeServiceImpl.deleteLike(likeId);
            return ResponseEntity.ok("Delete successful");
        }catch (LikeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like with id = %s not found".formatted(likeId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all likes for a post", description = "Retrieves the total number of likes for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post like count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/posts/{postId}/likes")
    public Long getAllPostLikes(@PathVariable("postId") Long postId) {
        return likeServiceImpl.showPostLikeSum(postId);
    }


    @Operation(summary = "Get all likes for a comment", description = "Retrieves the total number of likes for a specific comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment like count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/comments/{commentId}/likes")
    public Long getAllCommentLikes(@PathVariable("commentId") Long commentId) {
        return likeServiceImpl.showCommentLikeSum(commentId);
    }

}
