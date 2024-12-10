package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.dto.OpenPostDTO;
import org.thewhitemage13.dto.UpdatePostDTO;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.service.PostServiceImpl;

import java.util.List;

@Tag(name = "Post Controller", description = "Operations related to managing posts")
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @Operation(
            summary = "Get user ID by post ID",
            description = "Retrieves the user ID associated with a given post ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ID retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{postId}/user")
    public ResponseEntity<Long> getUserIdByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(postServiceImpl.getUserIdByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Find all posts by user ID",
            description = "Fetches all posts created by a user, identified by their user ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of posts returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OpenPostDTO>> findAllByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(postServiceImpl.openAllPostsByUserId(userId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get media URLs by user ID",
            description = "Fetches all media URLs associated with posts from a user, identified by their user ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of media URLs returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/user/{userId}/media")
    public ResponseEntity<List<String>> getMediaUrlByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postServiceImpl.getUrlsByUserId(userId));
    }

    @Operation(
            summary = "Get the number of posts by user ID",
            description = "Returns the number of posts created by the user identified by their user ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post count returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Integer> getPostCountByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postServiceImpl.getCountPostByUserId(userId));
    }

    @Operation(
            summary = "Get post details by post ID",
            description = "Retrieves the details of a post based on its unique post ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<OpenPostDTO> openPost(@PathVariable Long postId) throws PostNotFoundException {
        return ResponseEntity.ok(postServiceImpl.openPost(postId));
    }

    @Operation(
            summary = "Verify if a post exists",
            description = "Verifies whether a post exists by its ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification result returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/post-verification")
    public ResponseEntity<Boolean> postVerification(@RequestParam("postId") Long postId) {
        try {
            return ResponseEntity.ok(postServiceImpl.postVerification(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(
            summary = "Create a new post",
            description = "Creates a new post based on the provided post data.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping
    public ResponseEntity<String> createNewPost(@Valid @RequestBody CreatePostDTO createPostDTO) {
        try {
            postServiceImpl.createPost(createPostDTO);
            return ResponseEntity.ok("Post created");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Update an existing post",
            description = "Updates an existing post identified by its post ID with the provided post data.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostDTO updatePostDTO) {
        try {
            postServiceImpl.updatePost(postId, updatePostDTO);
            return ResponseEntity.ok("Post created");
        }catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with id = %s not found".formatted(postId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @Operation(
            summary = "Delete a post",
            description = "Deletes an existing post identified by its post ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            postServiceImpl.deletePost(postId);
            return ResponseEntity.ok("Post deleted");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with id = %S not found".formatted(postId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @Operation(
            summary = "Get posts by user ID",
            description = "Fetches all posts created by the user, identified by their user ID.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of posts returned successfully"),
            @ApiResponse(responseCode = "404", description = "Posts not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<CreatePostDTO>> getPostById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(postServiceImpl.getPostsByUserId(userId));
        }catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Get all posts",
            description = "Retrieves all posts in the system.",
            tags = {"Post Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of posts returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<CreatePostDTO>> getAllPosts() {
        try {
            return ResponseEntity.ok(postServiceImpl.getAllPosts());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
