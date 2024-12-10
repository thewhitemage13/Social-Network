package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.exceptions.LikeNotFoundException;
import org.thewhitemage13.service.LikeServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {
    @Mock
    private LikeServiceImpl likeServiceImpl;
    @InjectMocks
    private LikeController likeController;

    // Test getPostLikeCount
    @Test
    void getPostLikeCount_ReturnsLikeCount() {
        // given
        Long postId = 1L;
        Long likeCount = 10L;
        when(likeServiceImpl.getPostLikeCount(postId)).thenReturn(likeCount);

        // when
        ResponseEntity<Long> response = likeController.getPostLikeCount(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(likeCount, response.getBody());
    }

    @Test
    void getPostLikeCount_InternalServerError() {
        // given
        Long postId = 1L;
        when(likeServiceImpl.getPostLikeCount(postId)).thenThrow(new RuntimeException("Unexpected error"));

        // when
        ResponseEntity<Long> response = likeController.getPostLikeCount(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // Test postLike
    @Test
    void postLike_Success() {
        // given
        CreateLikePost createLikePost = new CreateLikePost();
        doNothing().when(likeServiceImpl).postLike(createLikePost);

        // when
        ResponseEntity<String> response = likeController.postLike(createLikePost);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post like successful", response.getBody());
        verify(likeServiceImpl).postLike(createLikePost);
    }

    @Test
    void postLike_InternalServerError() {
        // given
        CreateLikePost createLikePost = new CreateLikePost();
        doThrow(new RuntimeException("Unexpected error")).when(likeServiceImpl).postLike(createLikePost);

        // when
        ResponseEntity<String> response = likeController.postLike(createLikePost);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("An error occurred"));
    }

    // Test commentLike
    @Test
    void commentLike_Success() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment();
        doNothing().when(likeServiceImpl).commentLike(createLikeComment);

        // when
        ResponseEntity<String> response = likeController.commentLike(createLikeComment);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post like successful", response.getBody());
        verify(likeServiceImpl).commentLike(createLikeComment);
    }

    @Test
    void commentLike_InternalServerError() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment();
        doThrow(new RuntimeException("Unexpected error")).when(likeServiceImpl).commentLike(createLikeComment);

        // when
        ResponseEntity<String> response = likeController.commentLike(createLikeComment);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("An error occurred"));
    }

    // Test deleteLike
    @Test
    void deleteLike_Success() throws Exception{
        // given
        Long likeId = 1L;
        doNothing().when(likeServiceImpl).deleteLike(likeId);

        // when
        ResponseEntity<String> response = likeController.deleteLike(likeId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delete successful", response.getBody());
        verify(likeServiceImpl).deleteLike(likeId);
    }

    @Test
    void deleteLike_LikeNotFound() throws Exception{
        // given
        Long likeId = 1L;
        doThrow(new LikeNotFoundException("Like with id = 1 not found")).when(likeServiceImpl).deleteLike(likeId);

        // when
        ResponseEntity<String> response = likeController.deleteLike(likeId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Like with id = 1 not found", response.getBody());
    }

    @Test
    void deleteLike_InternalServerError() throws Exception {
        // given
        Long likeId = 1L;
        doThrow(new RuntimeException("Unexpected error")).when(likeServiceImpl).deleteLike(likeId);

        // when
        ResponseEntity<String> response = likeController.deleteLike(likeId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("An error occurred"));
    }

    // Test getAllPostLikes
    @Test
    void getAllPostLikes_ReturnsValidCount() {
        // given
        Long postId = 1L;
        Long likeSum = 20L;
        when(likeServiceImpl.showPostLikeSum(postId)).thenReturn(likeSum);

        // when
        Long response = likeController.getAllPostLikes(postId);

        // then
        assertNotNull(response);
        assertEquals(likeSum, response);
    }

    // Test getAllCommentLikes
    @Test
    void getAllCommentLikes_ReturnsValidCount() {
        // given
        Long commentId = 1L;
        Long likeSum = 15L;
        when(likeServiceImpl.showCommentLikeSum(commentId)).thenReturn(likeSum);

        // when
        Long response = likeController.getAllCommentLikes(commentId);

        // then
        assertNotNull(response);
        assertEquals(likeSum, response);
    }
}