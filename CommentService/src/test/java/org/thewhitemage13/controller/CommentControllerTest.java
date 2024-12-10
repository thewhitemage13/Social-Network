package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.exception.CommentNotFoundException;
import org.thewhitemage13.service.CommentServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Mock
    private CommentServiceImpl commentServiceImpl;
    @InjectMocks
    private CommentController commentController;

    // getCommentCountByPostId
    @Test
    void getCommentCountByPostId_ReturnsValidResponse() {
        // given
        Long postId = 1L;
        Long commentCount = 5L;
        Mockito.doReturn(commentCount).when(commentServiceImpl).getCountOfCommentsByPostId(postId);

        // when
        var response = commentController.getCommentCountByPostId(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentCount, response.getBody());
    }

    @Test
    void getCommentCountByPostId_InternalServerError() {
        // given
        Long postId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(commentServiceImpl).getCountOfCommentsByPostId(postId);

        // when
        var response = commentController.getCommentCountByPostId(postId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // getCommentUserIdByCommentId
    @Test
    void getCommentUserIdByCommentId_ReturnsValidResponse() {
        // given
        Long commentId = 1L;
        Long userId = 42L;
        Mockito.doReturn(userId).when(commentServiceImpl).getUserIdByCommentId(commentId);

        // when
        var response = commentController.getCommentUserIdByCommentId(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody());
    }

    @Test
    void getCommentUserIdByCommentId_InternalServerError() {
        // given
        Long commentId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(commentServiceImpl).getUserIdByCommentId(commentId);

        // when
        var response = commentController.getCommentUserIdByCommentId(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // commentVerification
    @Test
    void commentVerification_ReturnsTrue() {
        // given
        Long commentId = 1L;
        Mockito.doReturn(true).when(commentServiceImpl).commentVerification(commentId);

        // when
        var response = commentController.commentVerification(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void commentVerification_ReturnsFalse() {
        // given
        Long commentId = 1L;
        Mockito.doReturn(false).when(commentServiceImpl).commentVerification(commentId);

        // when
        var response = commentController.commentVerification(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void commentVerification_InternalServerError() {
        // given
        Long commentId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(commentServiceImpl).commentVerification(commentId);

        // when
        var response = commentController.commentVerification(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }

    // addComment
    @Test
    void addComment_ReturnsValidResponse() {
        // given
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        Mockito.doNothing().when(commentServiceImpl).addComment(commentCreateDto);

        // when
        var response = commentController.addComment(commentCreateDto);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment added", response.getBody());
    }

    @Test
    void addComment_InternalServerError() {
        // given
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(commentServiceImpl).addComment(commentCreateDto);

        // when
        var response = commentController.addComment(commentCreateDto);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // updateComment
    @Test
    void updateComment_ReturnsValidResponse() throws Exception {
        // given
        Long commentId = 1L;
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        Mockito.doNothing().when(commentServiceImpl).updateComment(commentId, commentCreateDto);

        // when
        var response = commentController.updateComment(commentId, commentCreateDto);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment updated", response.getBody());
    }

    @Test
    void updateComment_CommentNotFound() throws Exception {
        // given
        Long commentId = 1L;
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        Mockito.doThrow(new CommentNotFoundException("Comment with id = 1 not found"))
                .when(commentServiceImpl).updateComment(commentId, commentCreateDto);

        // when
        var response = commentController.updateComment(commentId, commentCreateDto);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Comment with id = 1 not found", response.getBody());
    }

    @Test
    void updateComment_InternalServerError() throws Exception {
        // given
        Long commentId = 1L;
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(commentServiceImpl).updateComment(commentId, commentCreateDto);

        // when
        var response = commentController.updateComment(commentId, commentCreateDto);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // deleteComment
    @Test
    void deleteComment_ReturnsValidResponse() throws Exception {
        // given
        Long commentId = 1L;
        Mockito.doNothing().when(commentServiceImpl).deleteComment(commentId);

        // when
        var response = commentController.deleteComment(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment deleted", response.getBody());
    }

    @Test
    void deleteComment_CommentNotFound() throws Exception {
        // given
        Long commentId = 1L;
        Mockito.doThrow(new CommentNotFoundException("Comment with id = 1 not found"))
                .when(commentServiceImpl).deleteComment(commentId);

        // when
        var response = commentController.deleteComment(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Comment with id = 1 not found", response.getBody());
    }

    @Test
    void deleteComment_InternalServerError() throws Exception {
        // given
        Long commentId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(commentServiceImpl).deleteComment(commentId);

        // when
        var response = commentController.deleteComment(commentId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }
}