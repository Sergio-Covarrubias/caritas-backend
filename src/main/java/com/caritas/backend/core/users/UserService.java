package com.caritas.backend.core.users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.users.dtos.UserRequest;
import com.caritas.backend.core.users.dtos.UserResponse;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user))
                .toList();
    }

    public UserResponse getUserById(String id) {
        UserEntity user = userRepository.findOneOrFail(id);

        return new UserResponse(user);
    }

    public UserResponse createUser(UserRequest request) {
        UserEntity user = new UserEntity(request.id(), request.firstName(), request.lastName(), request.email(),
                request.phoneNumber());
        UserEntity saved = userRepository.save(user);

        return new UserResponse(saved);
    }

    public UserResponse updateUser(String id, UserRequest request) {
        UserEntity user = userRepository.findOneOrFail(id);

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());

        UserEntity updated = userRepository.save(user);

        return new UserResponse(updated);
    }

    public void deleteUser(String id) {
        UserEntity user = userRepository.findOneOrFail(id);
        userRepository.delete(user);
    }
}
