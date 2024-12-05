package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.service.UserService;

import java.util.List;

@Tag(name = "User Controller", description = "Operations related to user management")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Update user information", description = "Updates an existing user's details using their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Phone number or email already taken"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId ,@Valid @RequestBody CreateUserDTO createUserDao) {
        try {
            userService.updateUser(userId, createUserDao);
            return ResponseEntity.ok("User updated successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        } catch (PhoneNumberAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number = %s already taken".formatted(createUserDao.getPhone()));
        } catch (EmailBusyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address = %s is already in use".formatted(createUserDao.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAll() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user's details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<String> getById(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId).toString());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Check user existence", description = "Checks if a user exists by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User existence check completed")
    })
    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.checkUserId(userId));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Phone number or email already taken"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserDTO createUserDao) {
        try {
            userService.createUser(createUserDao);
            return ResponseEntity.ok("User created successfully");
        } catch (EmailBusyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email = %s already exists".formatted(createUserDao.getEmail()));
        } catch (PhoneNumberAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with phone number = %s already exists".formatted(createUserDao.getPhone()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
