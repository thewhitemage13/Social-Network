package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.dto.OpenPostDTO;
import org.thewhitemage13.dto.UpdatePostDTO;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.service.PostServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostServiceImpl postServiceImpl;
    @InjectMocks
    private PostController postController;

    // getUserIdByPostId

    @Test
    void testGetUserIdByPostId_Success() {
        // given
        Long postId = 1L;
        Long userId = 2L;
        Mockito.doReturn(userId).when(postServiceImpl).getUserIdByPostId(postId);

        // when
        ResponseEntity<Long> response = postController.getUserIdByPostId(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody());
    }

    @Test
    void testGetUserIdByPostId_InternalServerError() {
        // given
        Long postId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(postServiceImpl).getUserIdByPostId(postId);

        // when
        ResponseEntity<Long> response = postController.getUserIdByPostId(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // findAllByUserId

    @Test
    void testFindAllByUserId_Success() {
        // given
        Long userId = 1L;
        List<OpenPostDTO> posts = List.of(new OpenPostDTO());
        Mockito.doReturn(posts).when(postServiceImpl).openAllPostsByUserId(userId);

        // when
        ResponseEntity<List<OpenPostDTO>> response = postController.findAllByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void testFindAllByUserId_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(postServiceImpl).openAllPostsByUserId(userId);

        // when
        ResponseEntity<List<OpenPostDTO>> response = postController.findAllByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // getMediaUrlByUserId

    @Test
    void testGetMediaUrlByUserId_Success() {
        // given
        Long userId = 1L;
        List<String> mediaUrls = List.of("url1", "url2");
        Mockito.doReturn(mediaUrls).when(postServiceImpl).getUrlsByUserId(userId);

        // when
        ResponseEntity<List<String>> response = postController.getMediaUrlByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mediaUrls, response.getBody());
    }

    // getPostCountByUserId

    @Test
    void testGetPostCountByUserId_Success() {
        // given
        Long userId = 1L;
        Long postCount = 5L;
        Mockito.doReturn(postCount).when(postServiceImpl).getCountPostByUserId(userId);

        // when
        ResponseEntity<Integer> response = postController.getPostCountByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postCount, response.getBody());
    }

    // openPost

    @Test
    void testOpenPost_Success() throws PostNotFoundException {
        // given
        Long postId = 1L;
        OpenPostDTO postDTO = new OpenPostDTO();
        Mockito.doReturn(postDTO).when(postServiceImpl).openPost(postId);

        // when
        ResponseEntity<OpenPostDTO> response = postController.openPost(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDTO, response.getBody());
    }

    // postVerification

    @Test
    void testPostVerification_Success() {
        // given
        Long postId = 1L;
        Boolean isVerified = true;
        Mockito.doReturn(isVerified).when(postServiceImpl).postVerification(postId);

        // when
        ResponseEntity<Boolean> response = postController.postVerification(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(isVerified, response.getBody());
    }

    @Test
    void testPostVerification_InternalServerError() {
        // given
        Long postId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(postServiceImpl).postVerification(postId);

        // when
        ResponseEntity<Boolean> response = postController.postVerification(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }

    // createNewPost

    @Test
    void testCreateNewPost_Success() throws Exception {
        // given
        CreatePostDTO createPostDTO = new CreatePostDTO();
        Mockito.doNothing().when(postServiceImpl).createPost(createPostDTO);

        // when
        ResponseEntity<String> response = postController.createNewPost(createPostDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post created", response.getBody());
    }

    @Test
    void testCreateNewPost_InternalServerError() throws Exception {
        // given
        CreatePostDTO createPostDTO = new CreatePostDTO();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(postServiceImpl).createPost(createPostDTO);

        // when
        ResponseEntity<String> response = postController.createNewPost(createPostDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // updatePost

    @Test
    void testUpdatePost_Success() throws Exception {
        // given
        Long postId = 1L;
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        Mockito.doNothing().when(postServiceImpl).updatePost(postId, updatePostDTO);

        // when
        ResponseEntity<String> response = postController.updatePost(postId, updatePostDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post created", response.getBody());
    }

    @Test
    void testUpdatePost_PostNotFound() throws Exception {
        // given
        Long postId = 1L;
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        Mockito.doThrow(new PostNotFoundException("Post not found")).when(postServiceImpl).updatePost(postId, updatePostDTO);

        // when
        ResponseEntity<String> response = postController.updatePost(postId, updatePostDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post with id = 1 not found", response.getBody());
    }

    // deletePost

    @Test
    void testDeletePost_Success() throws Exception {
        // given
        Long postId = 1L;
        Mockito.doNothing().when(postServiceImpl).deletePost(postId);

        // when
        ResponseEntity<String> response = postController.deletePost(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post deleted", response.getBody());
    }

    @Test
    void testDeletePost_PostNotFound() throws Exception {
        // given
        Long postId = 1L;
        Mockito.doThrow(new PostNotFoundException("Post not found")).when(postServiceImpl).deletePost(postId);

        // when
        ResponseEntity<String> response = postController.deletePost(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post with id = 1 not found", response.getBody());
    }

    @Test
    void testDeletePost_InternalServerError() throws Exception {
        // given
        Long postId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(postServiceImpl).deletePost(postId);

        // when
        ResponseEntity<String> response = postController.deletePost(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }
}