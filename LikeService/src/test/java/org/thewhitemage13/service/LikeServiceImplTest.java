package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.entity.Like;
import org.thewhitemage13.exception.LikeNotFoundException;
import org.thewhitemage13.repository.LikeRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @InjectMocks
    private LikeServiceImpl likeServiceImpl;

    @Test
    void deleteAllByCommentId_DeletesLikesSuccessfully() throws LikeNotFoundException {
        // given
        Long commentId = 1L;
        Like like = new Like(1L, null, commentId, 1L, LocalDateTime.now());
        when(likeRepository.findAllByCommentId(commentId)).thenReturn(Collections.singletonList(like));
        when(likeRepository.findById(like.getLikeId())).thenReturn(Optional.of(like));

        // when
        likeServiceImpl.deleteAllByCommentId(commentId);

        // then
        verify(likeRepository, times(1)).delete(like);
        verify(likeRepository, times(1)).findById(like.getLikeId());
    }

    @Test
    void deleteAllByCommentId_LikesNotFound() {
        // given
        Long commentId = 1L;
        when(likeRepository.findAllByCommentId(commentId)).thenReturn(Collections.emptyList());

        // when & then
        assertDoesNotThrow(() -> likeServiceImpl.deleteAllByCommentId(commentId));
        verify(likeRepository, never()).delete(any());
    }

    @Test
    void postLike_ValidatesAndSavesPostLike() {
        // given
        CreateLikePost createLikePost = new CreateLikePost(1L, 1L);
        Like like = new Like(null, 1L, null, 1L, LocalDateTime.now());
        when(likeRepository.save(any())).thenReturn(like);

        // when
        likeServiceImpl.postLike(createLikePost);

        // then
        verify(validationServiceImpl).validatePostLike(createLikePost);
        verify(kafkaTemplate).executeInTransaction(any());
    }

    @Test
    void commentLike_ValidatesAndSavesCommentLike() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment(1L, 1L);
        Like like = new Like(null, null, 1L, 1L, LocalDateTime.now());
        when(likeRepository.save(any())).thenReturn(like);

        // when
        likeServiceImpl.commentLike(createLikeComment);

        // then
        verify(validationServiceImpl).validateCommentLike(createLikeComment);
        verify(kafkaTemplate).executeInTransaction(any());
    }

    @Test
    void deleteLike_DeletesLikeSuccessfully() throws LikeNotFoundException {
        // given
        Long likeId = 1L;
        Like like = new Like(likeId, 1L, null, 1L, LocalDateTime.now());
        when(likeRepository.findById(likeId)).thenReturn(Optional.of(like));

        // when
        likeServiceImpl.deleteLike(likeId);

        // then
        verify(likeRepository).delete(like);
        verify(kafkaTemplate).executeInTransaction(any());
    }

    @Test
    void deleteLike_LikeNotFound() {
        // given
        Long likeId = 1L;
        when(likeRepository.findById(likeId)).thenReturn(Optional.empty());

        // when & then
        LikeNotFoundException exception = assertThrows(LikeNotFoundException.class, () -> likeServiceImpl.deleteLike(likeId));
        assertEquals("Like with id = 1 not found", exception.getMessage());
    }

    @Test
    void showPostLikeSum_ReturnsCorrectSum() {
        // given
        Long postId = 1L;
        List<Like> likes = Arrays.asList(
                new Like(1L, postId, null, 1L, LocalDateTime.now()),
                new Like(2L, postId, null, 2L, LocalDateTime.now())
        );
        when(likeRepository.findAllByPostId(postId)).thenReturn(likes);

        // when
        Long result = likeServiceImpl.showPostLikeSum(postId);

        // then
        assertEquals(2, result);
    }

    @Test
    void showCommentLikeSum_ReturnsCorrectSum() {
        // given
        Long commentId = 1L;
        List<Like> likes = Arrays.asList(
                new Like(1L, null, commentId, 1L, LocalDateTime.now()),
                new Like(2L, null, commentId, 2L, LocalDateTime.now())
        );
        when(likeRepository.findAllByCommentId(commentId)).thenReturn(likes);

        // when
        Long result = likeServiceImpl.showCommentLikeSum(commentId);

        // then
        assertEquals(2, result);
    }
}