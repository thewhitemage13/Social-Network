package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.entity.Comment;
import org.thewhitemage13.exceptions.CommentNotFoundException;
import org.thewhitemage13.repository.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @Test
    void getUserIdByCommentId_ReturnsUserId() {
        // given
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setUserId(42L);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        Long userId = commentServiceImpl.getUserIdByCommentId(commentId);

        // then
        assertNotNull(userId);
        assertEquals(42L, userId);
    }

    @Test
    void getUserIdByCommentId_ThrowsExceptionWhenCommentNotFound() {
        // given
        Long commentId = 1L;
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> commentServiceImpl.getUserIdByCommentId(commentId));
    }

    @Test
    void deleteComment_ThrowsExceptionWhenCommentNotFound() {
        // given
        Long commentId = 1L;
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CommentNotFoundException.class, () -> commentServiceImpl.deleteComment(commentId));
    }

    @Test
    void updateComment_ThrowsExceptionWhenCommentNotFound() {
        // given
        Long commentId = 1L;
        CommentCreateDto dto = new CommentCreateDto();
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CommentNotFoundException.class, () -> commentServiceImpl.updateComment(commentId, dto));
    }

    @Test
    void getAllByPostId_ReturnsComments() throws CommentNotFoundException {
        // given
        Long postId = 1L;
        List<Comment> comments = List.of(new Comment(), new Comment());
        Mockito.when(commentRepository.findAllByPostId(postId)).thenReturn(Optional.of(comments));

        // when
        List<Comment> result = commentServiceImpl.getAllByPostId(postId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllByPostId_ThrowsExceptionWhenCommentsNotFound() {
        // given
        Long postId = 1L;
        Mockito.when(commentRepository.findAllByPostId(postId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CommentNotFoundException.class, () -> commentServiceImpl.getAllByPostId(postId));
    }
}