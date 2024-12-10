package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.exception.CommentNotFoundException;
import org.thewhitemage13.service.CommentServiceImpl;

@Tag(name = "Comment Controller", description = "Operations related to managing comments")
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentServiceImpl commentServiceImpl;

    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    @Operation(summary = "Get comment count by post ID", description = "Retrieves the count of comments for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/posts/{postId}/count")
    public ResponseEntity<Long> getCommentCountByPostId(@PathVariable("postId") Long postId) {
        try {
            return ResponseEntity.ok(commentServiceImpl.getCountOfCommentsByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get user ID by comment ID", description = "Retrieves the user ID associated with a specific comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ID retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{commentId}/user")
    public ResponseEntity<Long> getCommentUserIdByCommentId(@PathVariable("commentId") Long commentId) {
        try {
            return ResponseEntity.ok(commentServiceImpl.getUserIdByCommentId(commentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Verify comment", description = "Checks if a specific comment is valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment verification status retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{commentId}/verify")
    public ResponseEntity<Boolean> commentVerification(@PathVariable("commentId") Long commentId) {
        try {
            return ResponseEntity.ok(commentServiceImpl.commentVerification(commentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(summary = "Add a comment", description = "Adds a new comment to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment added successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentCreateDto commentCreateDto) {
        try {
            commentServiceImpl.addComment(commentCreateDto);
            return ResponseEntity.ok("Comment added");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Update a comment", description = "Updates an existing comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long commentId ,@RequestBody CommentCreateDto commentCreateDto) {
        try {
            commentServiceImpl.updateComment(commentId, commentCreateDto);
            return ResponseEntity.ok("Comment updated");
        }catch (CommentNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment with id = %s not found".formatted(commentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            commentServiceImpl.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted");
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment with id = %s not found".formatted(commentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @Operation(summary = "Get all comments by post ID", description = "Retrieves all comments for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Comments not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/posts/{postId}")
    public ResponseEntity<String> getAllComments(@PathVariable("postId") Long postId) {
        try {
            return ResponseEntity.ok(commentServiceImpl.getAllByPostId(postId).toString());
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comments with post id = %s not found".formatted(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
