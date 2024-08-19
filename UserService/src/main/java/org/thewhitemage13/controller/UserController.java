package org.thewhitemage13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.dao.GetUserDao;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId ,@RequestBody CreateUserDao createUserDao) {
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

    @GetMapping("/get-all")
    public ResponseEntity<List<GetUserDao>> getAll() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<String> getById(@RequestParam("userId") Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId).toString());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = %s not found".formatted(userId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.checkUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDao createUserDao) {
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
