package com.caritas.backend.core.users;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.users.dtos.UpdateUserRequest;
import com.caritas.backend.core.users.dtos.CreateUserRequest;
import com.caritas.backend.core.users.dtos.UserSerialized;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserSerialized> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserSerialized getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public UserSerialized createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserSerialized updateUser(@PathVariable String id, @Valid  @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
