package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.exception.EmailAlreadyTakenException;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.exception.UsernameIsBusyException;
import org.thewhitemage13.service.UserServiceImpl;

import java.util.List;

@Tag(name = "User Controller", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user's details using their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Add multiple users", description = "Adds multiple users in a single request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users added successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> addMultipleUsers(@RequestBody List<CreateUserDTO> users) {
        try {
            userService.addUsers(users);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get users by IDs", description = "Retrieves a list of user subscription details by their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<UserSubscriptionDTO> getUsersByIds(@RequestParam List<Long> ids) {
        return userService.getUsersByIds(ids);
    }

    @Operation(summary = "Get public user details", description = "Retrieves public details of a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/open")
    public ResponseEntity<OpenUserDTO> getPublicUserDetails(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.openUser(userId));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get username by ID", description = "Retrieves a username by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/username")
    public ResponseEntity<String> getUserNameById(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(userService.getUsernameById(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Verify user existence", description = "Checks if a user exists by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification status retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/verify")
    public ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId) {
        try {
            boolean isCreated = userService.userVerification(userId);
            return ResponseEntity.ok(isCreated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(summary = "Register new user", description = "Registers a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Email or username already taken"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.registerNewUser(createUserDTO);
            return ResponseEntity.ok("User created successfully");
        } catch (UsernameIsBusyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username = %s is busy!".formatted(createUserDTO.getUsername()));
        } catch (EmailAlreadyTakenException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email = %s is already taken!".formatted(createUserDTO.getEmail()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Update user details", description = "Updates user details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Email or username already taken"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.updateUserProfile(userId, createUserDTO);
            return ResponseEntity.ok("User updated");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id = %s not found!".formatted(userId));
        } catch (UsernameIsBusyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username = %s is busy!".formatted(createUserDTO.getUsername()));
        } catch (EmailAlreadyTakenException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email = %s is already taken!".formatted(createUserDTO.getEmail()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Find user by username", description = "Finds user information by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/info/{username}")
    public Object findUser(@PathVariable String username) {
        try {
            return userService.getInformationAboutUser(username);
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id = %s not found!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id = %s not found!".formatted(userId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
