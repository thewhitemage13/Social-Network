package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.dto.OpenPostDTO;
import org.thewhitemage13.dto.UpdatePostDTO;
import org.thewhitemage13.entity.Post;
import org.thewhitemage13.exception.PostNotFoundException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.PostServiceInterface;
import org.thewhitemage13.processor.PostProcessor;
import org.thewhitemage13.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link PostServiceInterface}, providing services for managing posts.
 * <p>
 * This service offers functionalities such as creating, updating, deleting posts, and fetching
 * post details for a given user. It utilizes caching for frequently accessed data to improve
 * performance. The service also interacts with Kafka to publish events related to posts.
 * </p>
 *
 * <p>
 * The service also handles post validation (media, user) and interacts with external services
 * (via {@link UserClient}) to enrich post data with user information.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class PostServiceImpl implements PostServiceInterface {
    private final PostRepository postRepository;
    private final ValidationServiceImpl validationServiceImpl;
    private final UserClient userClient;
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final PostProcessor postProcessor;

    /**
     * Constructs a new {@code PostServiceImpl} with the specified dependencies.
     * <p>
     * The constructor injects the required dependencies such as the repository for posts,
     * validation services, Kafka template for event publishing, and a processor for handling
     * post-related logic.
     * </p>
     *
     * @param postRepository the {@link PostRepository} for managing post entities
     * @param validationServiceImpl the service for validating posts
     * @param kafkaTemplate the {@link KafkaTemplate} for interacting with Kafka
     * @param userClient the {@link UserClient} for fetching user information
     * @param postProcessor the {@link PostProcessor} for processing post-related data
     */
    @Autowired
    public PostServiceImpl
            (
                    PostRepository postRepository,
                    ValidationServiceImpl validationServiceImpl,
                    KafkaTemplate<Long, Object> kafkaTemplate,
                    UserClient userClient,
                    PostProcessor postProcessor
            ) {
        this.postRepository = postRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
        this.userClient = userClient;
        this.postProcessor = postProcessor;
    }

    /**
     * Retrieves the user ID associated with the given post ID.
     * <p>
     * This method checks the post repository for the post with the given ID,
     * and if found, returns the associated user ID. The result is cached to improve performance.
     * </p>
     *
     * @param postId the ID of the post to search for
     * @return the user ID associated with the post
     */
    @Override
    @Cacheable(value = "postsByUserId", key = "#userId")
    public Long getUserIdByPostId(Long postId) {
        return postRepository.findById(postId).get().getUserId();
    }

    /**
     * Deletes all posts associated with a given user ID.
     * <p>
     * This method retrieves all posts by the given user ID and deletes them.
     * The cache entries related to the posts are evicted for consistency.
     * </p>
     *
     * @param userId the ID of the user whose posts need to be deleted
     * @throws PostNotFoundException if no posts for the user are found
     */
    @Override
    @CacheEvict(value = {"postsByUserId", "openPostsByUserId", "postCountByUserId", "postUrlsByUserId"}, key = "#userId")
    public void deleteAllByUserId(Long userId) throws PostNotFoundException {
        List<Post> deleteAll = postRepository.findAllByUserId(userId)
                .orElseThrow(() -> new PostNotFoundException("Post with user id = %s not found".formatted(userId)));
        for (Post post : deleteAll) {
            deletePost(post.getPostId());
        }
    }

    /**
     * Retrieves a list of media URLs associated with posts by the given user ID.
     * <p>
     * This method checks the post repository for all posts by the user and
     * returns a list of their media URLs. The result is cached to improve performance.
     * </p>
     *
     * @param userId the ID of the user whose media URLs are to be retrieved
     * @return a list of media URLs associated with the user's posts
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    @Cacheable(value = "postUrlsByUserId", key = "#userId")
    public List<String> getUrlsByUserId(Long userId) {
        List<Post> get = postRepository.findAllByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        List<String> urls = new ArrayList<>();
        for (Post post : get) {
            urls.add(post.getMediaUrl());
        }
        return urls;
    }

    /**
     * Retrieves the count of posts for the given user ID.
     * <p>
     * This method checks the post repository for the count of posts by the given user
     * and returns the count. The result is cached to improve performance.
     * </p>
     *
     * @param userId the ID of the user whose post count is to be retrieved
     * @return the count of posts by the user
     */
    @Override
    @Cacheable(value = "postCountByUserId", key = "#userId")
    public Integer getCountPostByUserId(Long userId) {
        return postRepository.countByUserId(userId);
    }

    /**
     * Retrieves a list of posts made by the given user ID and formats them for display.
     * <p>
     * This method fetches all posts made by the user and processes them into a list
     * of {@link OpenPostDTO} objects. The result is cached for improved performance.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be retrieved
     * @return a list of formatted posts made by the user
     * @throws UserNotFoundException if no posts are found for the user
     */
    @Override
    @Cacheable(value = "openPostsByUserId", key = "#userId")
    public List<OpenPostDTO> openAllPostsByUserId(Long userId) {
        List<Post> openAll = postRepository.findAllByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        List<OpenPostDTO> openPosts = new ArrayList<>();
        for (Post post : openAll) {
            OpenPostDTO openPostDTO = new OpenPostDTO();
            openPostDTO.setMediaUrl(post.getMediaUrl());
            Long likes = validationServiceImpl.validateLike(post.getPostId());
            Long comments = validationServiceImpl.validateComment(post.getPostId());
            ResponseEntity<String> name = userClient.getUserNameById(post.getUserId());
            String username = name.getBody();
            openPostDTO.setUsername(username);
            openPostDTO.setLikes(likes);
            openPostDTO.setComments(comments);
            openPostDTO.setContent(post.getContent());
            openPosts.add(openPostDTO);
        }
        return openPosts;
    }

    /**
     * Retrieves a specific post by its ID and formats it for display.
     * <p>
     * This method fetches a post by its ID and formats it into an {@link OpenPostDTO} object.
     * The result is cached to improve performance.
     * </p>
     *
     * @param postId the ID of the post to retrieve
     * @return the formatted post
     * @throws PostNotFoundException if the post with the given ID is not found
     */
    @Override
    @Cacheable(value = "postById", key = "#postId")
    public OpenPostDTO openPost(Long postId) throws PostNotFoundException {
        Post open = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id = %s not found".formatted(postId)));
        String mediaUrl = open.getMediaUrl();
        String content = open.getContent();
        Long likes = validationServiceImpl.validateLike(postId);
        Long comments = validationServiceImpl.validateComment(postId);
        ResponseEntity<String> name = userClient.getUserNameById(open.getUserId());
        String username = name.getBody();
        OpenPostDTO openPostDTO = new OpenPostDTO();
        openPostDTO.setMediaUrl(mediaUrl);
        openPostDTO.setContent(content);
        openPostDTO.setLikes(likes);
        openPostDTO.setComments(comments);
        openPostDTO.setUsername(username);
        return openPostDTO;
    }


    /**
     * Verifies the existence of a post with the given post ID.
     * <p>
     * This method checks if a post with the specified ID exists in the repository.
     * </p>
     *
     * @param postId the ID of the post to verify
     * @return {@code true} if the post exists, otherwise {@code false}
     */
    @Override
    public boolean postVerification (Long postId) {
        return postRepository.existsById(postId);
    }

    /**
     * Creates a new post with the specified data.
     * <p>
     * This method validates the media URL and user data, and creates a new post in
     * the repository. A "post.created" event is then published to Kafka to notify
     * other services of the new post creation.
     * </p>
     *
     * @param createPostDTO the data for the new post
     */
    @Override
    @CacheEvict(value = "postsByUserId", key = "#createPostDTO.userId", allEntries = true)
    public void createPost(CreatePostDTO createPostDTO) {
        validationServiceImpl.validateMedia(createPostDTO);
        validationServiceImpl.validateUser(createPostDTO);
        Post post = new Post
                (
                        createPostDTO.getUserId(),
                        createPostDTO.getContent(),
                        createPostDTO.getMediaUrl(),
                        LocalDateTime.now()
                );
        postRepository.save(post);
        PostEvent postEvent = postProcessor.getPostEvent(post);
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("post.created", post.getPostId(), postEvent);
//            return null;
//        });

        kafkaTemplate.send("post.created", post.getPostId(), postEvent);
    }

    /**
     * Updates an existing post with the specified data.
     * <p>
     * This method updates the content and media URL of an existing post and publishes
     * a "post.updated" event to Kafka to notify other services of the update.
     * </p>
     *
     * @param userId the ID of the user requesting the update
     * @param updatePostDTO the data to update the post with
     * @throws PostNotFoundException if the post with the given ID is not found
     */
    @Override
    @CacheEvict(value = "postById", key = "#postId")
    public void updatePost(Long userId, UpdatePostDTO updatePostDTO) throws PostNotFoundException {
        Post update = postRepository.findById(userId)
                .orElseThrow(() -> new PostNotFoundException("Post with id = %s not found".formatted(userId)));
        update.setContent(updatePostDTO.getContent());
        update.setMediaUrl(updatePostDTO.getMediaUrl());
        update.setUpdatedAt(LocalDateTime.now());
        postRepository.save(update);
        PostEvent postEvent = postProcessor.getPostEvent(update);
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("post.updated", update.getPostId(), postEvent);
//            return null;
//        });

        kafkaTemplate.send("post.updated", update.getPostId(), postEvent);
    }

    /**
     * Deletes a post by its ID.
     * <p>
     * This method deletes the post from the repository and publishes a "post.deleted" event
     * to Kafka to notify other services of the post deletion.
     * </p>
     *
     * @param postId the ID of the post to delete
     * @throws PostNotFoundException if the post with the given ID is not found
     */
    @Override
    @CacheEvict(value = "postById", key = "#postId")
    public void deletePost(Long postId) throws PostNotFoundException {
        Post deletePost = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundException("Post with id = %s not found".formatted(postId)));
        postRepository.delete(deletePost);
        PostEvent postEvent = postProcessor.getPostEvent(deletePost);
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("post.deleted", deletePost.getPostId(), postEvent);
//            return null;
//        });

        kafkaTemplate.send("post.deleted", deletePost.getPostId(), postEvent);
    }

    /**
     * Retrieves a list of posts made by a specific user and formats them for display.
     * <p>
     * This method fetches all posts by the user and processes them into a list of DTOs
     * for easy display. The result is cached to improve performance.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be retrieved
     * @return a list of formatted posts made by the user
     * @throws PostNotFoundException if no posts for the user are found
     */
    @Override
    @Cacheable(value = "postsByUserIdList", key = "#userId")
    public List<CreatePostDTO> getPostsByUserId(Long userId) throws PostNotFoundException {
        List<Post> post = postRepository.findAllByUserId(userId)
                .orElseThrow(() -> new PostNotFoundException("Posts with user id = %s is not found".formatted(userId)));
        return postProcessor.getPostDTOS(post);
    }

    /**
     * Retrieves all posts from the system and formats them for display.
     * <p>
     * This method retrieves all posts and processes them into a list of DTOs for
     * easy display. The result is cached to improve performance.
     * </p>
     *
     * @return a list of all posts in the system
     */
    @Override
    @Cacheable(value = "allPosts")
    public List<CreatePostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        System.out.println(posts);
        return postProcessor.getPostDTOS(posts);
    }
}
