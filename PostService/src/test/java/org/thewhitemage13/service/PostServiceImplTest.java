package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.dto.UpdatePostDTO;
import org.thewhitemage13.entity.Post;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.processor.PostProcessor;
import org.thewhitemage13.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private UserClient userClient;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @Mock
    private PostProcessor postProcessor;
    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @Test
    void testGetUserIdByPostId() {
        // given
        Long postId = 1L;
        Long expectedUserId = 100L;
        Post post = new Post(expectedUserId, "content", "http://media.url", LocalDateTime.now());
        post.setPostId(postId);

        // mock repository behavior
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        Long userId = postServiceImpl.getUserIdByPostId(postId);

        // then
        assertEquals(expectedUserId, userId);
    }

    @Test
    void testOpenPost_PostNotFound() {
        // given
        Long postId = 1L;

        // mock repository behavior to simulate post not found
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when & then
        PostNotFoundException thrown = assertThrows(PostNotFoundException.class,
                () -> postServiceImpl.openPost(postId));
        assertEquals("Post with id = 1 not found", thrown.getMessage());
    }

    @Test
    void testCreatePost() {
        // given
        CreatePostDTO createPostDTO = new CreatePostDTO(100L, "content", "http://media.url");
        Post post = new Post(createPostDTO.getUserId(), createPostDTO.getContent(), createPostDTO.getMediaUrl(), LocalDateTime.now());

        // mock validations and repository save
        Mockito.doNothing().when(validationServiceImpl).validateMedia(createPostDTO);
        Mockito.doNothing().when(validationServiceImpl).validateUser(createPostDTO);
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        // when
        postServiceImpl.createPost(createPostDTO);

        // then
        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
        Mockito.verify(kafkaTemplate, Mockito.times(1)).executeInTransaction(Mockito.any());
    }

    @Test
    void testDeletePost_PostNotFound() {
        // given
        Long postId = 1L;

        // mock repository behavior to simulate post not found
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when & then
        PostNotFoundException thrown = assertThrows(PostNotFoundException.class,
                () -> postServiceImpl.deletePost(postId));
        assertEquals("Post with id = 1 not found", thrown.getMessage());
    }

    @Test
    void testUpdatePost_PostNotFound() throws PostNotFoundException {
        // given
        Long postId = 1L;
        UpdatePostDTO updatePostDTO = new UpdatePostDTO("Updated content", "http://new-media.url");

        // mock repository behavior to simulate post not found
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when & then
        PostNotFoundException thrown = assertThrows(PostNotFoundException.class,
                () -> postServiceImpl.updatePost(postId, updatePostDTO));
        assertEquals("Post with id = 1 not found", thrown.getMessage());
    }

    @Test
    void testDeleteAllByUserId_PostNotFound() {
        // given
        Long userId = 100L;

        // mock repository behavior to simulate no posts found for user
        Mockito.when(postRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        PostNotFoundException thrown = assertThrows(PostNotFoundException.class,
                () -> postServiceImpl.deleteAllByUserId(userId));
        assertEquals("Post with user id = 100 not found", thrown.getMessage());
    }

    @Test
    void testGetUrlsByUserId_UserNotFound() {
        // given
        Long userId = 100L;

        // mock repository behavior to simulate no posts for the user
        Mockito.when(postRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> postServiceImpl.getUrlsByUserId(userId));
        assertEquals("User with id = 100 not found", thrown.getMessage());
    }

    @Test
    void testGetCountPostByUserId() {
        // given
        Long userId = 100L;
        Integer expectedCount = 5;

        // mock repository behavior to simulate count
        Mockito.when(postRepository.countByUserId(userId)).thenReturn(expectedCount);

        // when
        Integer count = postServiceImpl.getCountPostByUserId(userId);

        // then
        assertEquals(expectedCount, count);
    }

    @Test
    void testOpenAllPostsByUserId_UserNotFound() {
        // given
        Long userId = 100L;

        // mock repository behavior to simulate no posts for the user
        Mockito.when(postRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> postServiceImpl.openAllPostsByUserId(userId));
        assertEquals("User with id = 100 not found", thrown.getMessage());
    }

    @Test
    void testPostVerification() {
        // given
        Long postId = 1L;

        // mock repository behavior to simulate post existence
        Mockito.when(postRepository.existsById(postId)).thenReturn(true);

        // when
        boolean exists = postServiceImpl.postVerification(postId);

        // then
        assertTrue(exists);
    }

    @Test
    void testGetPostsByUserId_PostNotFound() {
        // given
        Long userId = 100L;

        // mock repository behavior to simulate no posts for the user
        Mockito.when(postRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        PostNotFoundException thrown = assertThrows(PostNotFoundException.class,
                () -> postServiceImpl.getPostsByUserId(userId));
        assertEquals("Posts with user id = 100 is not found", thrown.getMessage());
    }

    @Test
    void testGetAllPosts() {
        // given
        List<Post> posts = Arrays.asList(
                new Post(1L, "content1", "http://media1.url", LocalDateTime.now()),
                new Post(2L, "content2", "http://media2.url", LocalDateTime.now())
        );

        // mock repository behavior
        Mockito.when(postRepository.findAll()).thenReturn(posts);

        // when
        List<CreatePostDTO> createPostDTOS = postServiceImpl.getAllPosts();

        // then
        assertNotNull(createPostDTOS);
        assertEquals(0, createPostDTOS.size());
    }


}