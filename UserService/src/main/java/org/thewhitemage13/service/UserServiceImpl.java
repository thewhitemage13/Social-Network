package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailAlreadyTakenException;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.exception.UsernameIsBusyException;
import org.thewhitemage13.interfaces.UserServiceInterface;
import org.thewhitemage13.processor.UserProcessorImpl;
import org.thewhitemage13.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link UserServiceInterface}, providing user management functionality.
 * <p>
 * This service layer handles operations such as user creation, updating, deletion,
 * and retrieval. It integrates with the {@link UserRepository}, validation logic, and Kafka for event publishing.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>User creation with validation and password encoding.</li>
 *     <li>Fetching user details using caching for improved performance.</li>
 *     <li>Integration with Kafka for publishing user-related events.</li>
 *     <li>Transactional support to ensure data consistency.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link UserRepository} for database interactions.</li>
 *     <li>{@link UserProcessorImpl} for converting user entities to DTOs.</li>
 *     <li>{@link ValidationServiceImpl} for data validation and auxiliary operations.</li>
 *     <li>{@link KafkaTemplate} for publishing user events.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepository userRepository;
    private final ValidationServiceImpl validationServiceImpl;
    private final UserProcessorImpl userProcessorImpl;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    /**
     * Constructs a new {@code UserServiceImpl} with required dependencies.
     *
     * @param userRepository the repository for user entity operations
     * @param validationServiceImpl the service for validating user data
     * @param kafkaTemplate the Kafka template for publishing events
     * @param userProcessorImpl the processor for handling user-related logic
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ValidationServiceImpl validationServiceImpl,
                           KafkaTemplate<Long, Object> kafkaTemplate,
                           UserProcessorImpl userProcessorImpl) {
        this.userRepository = userRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
        this.userProcessorImpl = userProcessorImpl;
    }

    /**
     * Adds a list of users by registering each user individually.
     *
     * @param users the list of {@link CreateUserDTO} to be registered
     * @throws NumberParseException if the phone number format is invalid
     */
    @Override
    public void addUsers(List<CreateUserDTO> users) throws NumberParseException {
        for (CreateUserDTO user : users) {
            registerNewUser(user);
        }
    }

    /**
     * Retrieves the username associated with a given user ID using caching.
     *
     * @param userId the unique ID of the user
     * @return the username of the user
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    @Cacheable(value = "usernames", key = "#userId")
    public String getUsernameById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        return user.getUsername();
    }

    /**
     * Retrieves a user's details as a {@link GetUserDTO} using caching.
     *
     * @param userId the unique ID of the user
     * @return a {@link GetUserDTO} containing the user's details
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    @Cacheable(value = "users", key = "#userId")
    public GetUserDTO getUserById(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        return userProcessorImpl.getGetUserDTO(user);
    }

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the unique ID of the user
     * @return {@code true} if the user exists; {@code false} otherwise
     */
    @Override
    public boolean userVerification(Long userId) {
        return userRepository.existsUserByUserId(userId);
    }

    /**
     * Retrieves information about a user by their username using caching.
     *
     * @param username the username of the user
     * @return a {@link GetUserDTO} containing the user's details
     * @throws UserNotFoundException if no user is found with the given username
     */
    @Override
    @Cacheable(value = "users", key = "#username")
    public GetUserDTO getInformationAboutUser(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User with username = %s not found".formatted(username)));
        return userProcessorImpl.getGetUserDTO(user);
    }

    /**
     * Deletes a user by their ID and publishes a deletion event via Kafka.
     *
     * @param userId the unique ID of the user to be deleted
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    @CacheEvict(value = {"users", "usernames"}, key = "#userId")
    public void deleteUser(Long userId) throws UserNotFoundException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User with id = %s not found".formatted(userId)));

        UserEvent userEvent = userProcessorImpl.getUserEvent(user);

        userRepository.delete(user);
//
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("user.deleted", user.getUserId(), userEvent);
//            return null;
//        });
//
        kafkaTemplate.send("user.deleted", user.getUserId(), userEvent);
    }

    /**
     * Retrieves a list of users by their IDs and maps them to {@link UserSubscriptionDTO}.
     *
     * @param userIds the list of user IDs
     * @return a list of {@link UserSubscriptionDTO}
     */
    @Override
    public List<UserSubscriptionDTO> getUsersByIds(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        List<UserSubscriptionDTO> subscriptions = new ArrayList<>();

        for (User user : users) {
            UserSubscriptionDTO userSubscriptionDTO = new UserSubscriptionDTO();
            userSubscriptionDTO.setUsername(user.getUsername());
            userSubscriptionDTO.setUserId(user.getUserId());
            subscriptions.add(userSubscriptionDTO);
        }

        return subscriptions;
    }

    /**
     * Opens detailed user information as an {@link OpenUserDTO}.
     *
     * @param userId the unique ID of the user
     * @return an {@link OpenUserDTO} containing public user information
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public OpenUserDTO openUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));

        String userName = user.getUsername();
        String profilePictureUrl = user.getProfilePictureUrl();

        List<String> mediaPostsUrl = validationServiceImpl.validateMedia(userId);
        Long countFollowing = validationServiceImpl.validateFollowing(userId);;
        Long countFollowers = validationServiceImpl.validateFollowers(userId);
        Long countPosts = validationServiceImpl.validatePost(userId);

        return userProcessorImpl
                .getOpenUserDTO(
                        userName,
                        profilePictureUrl,
                        mediaPostsUrl,
                        countFollowing,
                        countFollowers,
                        countPosts
                );
    }

    /**
     * Updates the profile of an existing user and publishes an update event via Kafka.
     *
     * @param userId the unique ID of the user to be updated
     * @param createUserDTO the DTO containing the updated user data
     * @throws UserNotFoundException if no user is found with the given ID
     * @throws UsernameIsBusyException if the new username is already in use
     * @throws EmailAlreadyTakenException if the new email is already taken
     * @throws EmailBusyException if the email is invalid
     * @throws NumberParseException if the phone number format is invalid
     */
    @Override
    @CacheEvict(value = {"users", "usernames"}, key = "#userId")
    public void updateUserProfile(Long userId, CreateUserDTO createUserDTO) throws UsernameIsBusyException, UserNotFoundException, EmailAlreadyTakenException, EmailBusyException, NumberParseException {
        validationServiceImpl.validatePicture(createUserDTO);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        String phoneNum = validationServiceImpl.validateUpdateUser(createUserDTO, user);

        validationServiceImpl.validatePassword(createUserDTO.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        userProcessorImpl.userCreateProcessor(createUserDTO, user, phoneNum);

        userRepository.save(user);

        UserEvent userEvent = userProcessorImpl.getUserEvent(user);

//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("user.updated", user.getUserId(), userEvent);
//            return null;
//        });

        kafkaTemplate.send("user.updated", user.getUserId(), userEvent);
    }

    /**
     * Registers a new user in the system and publishes a creation event via Kafka.
     *
     * @param createUserDTO the DTO containing the new user's data
     * @throws UsernameIsBusyException if the username is already in use
     * @throws EmailAlreadyTakenException if the email is already taken
     * @throws EmailBusyException if the email is invalid
     * @throws NumberParseException if the phone number format is invalid
     */
    @Override
    public void registerNewUser(CreateUserDTO createUserDTO) throws UsernameIsBusyException, EmailAlreadyTakenException, EmailBusyException, NumberParseException {
        validationServiceImpl.validatePicture(createUserDTO);
        validationServiceImpl.validateUsername(createUserDTO.getUsername());
        validationServiceImpl.validateEmail(createUserDTO.getEmail());
        validationServiceImpl.validatePassword(createUserDTO.getPassword());

        String phoneNumber = validationServiceImpl
                .validatePhoneNumber(createUserDTO.getPhoneNumber(), createUserDTO.getRegion());

        User registerUser = new User();
        registerUser.setCreatedAt(LocalDateTime.now());
        userProcessorImpl.userCreateProcessor(createUserDTO, registerUser, phoneNumber);
        userRepository.save(registerUser);

        UserEvent event = userProcessorImpl.getUserEvent(registerUser);

//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("user.updated", registerUser.getUserId(), event);
//            return null;
//        });
//
        kafkaTemplate.send("user.updated", registerUser.getUserId(), event);
    }
}
