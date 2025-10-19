package com.caritas.backend.core.users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.users.dtos.UpdateUserRequest;
import com.caritas.backend.core.users.dtos.CreateUserRequest;
import com.caritas.backend.core.users.dtos.UserSerialized;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserSerialized> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserSerialized(user))
                .toList();
    }

    public UserSerialized getUserById(String id) {
        UserEntity user = userRepository.findOneOrFail(id);

        return new UserSerialized(user);
    }

    public UserSerialized createUser(CreateUserRequest request) {
        UserEntity user = new UserEntity(request.id(), request.firstName(), request.lastName(), request.phoneNumber());
        UserEntity saved = userRepository.save(user);

        return new UserSerialized(saved);
    }

    public UserSerialized updateUser(String id, UpdateUserRequest request) {
        UserEntity user = userRepository.findOneOrFail(id);

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());

        UserEntity updated = userRepository.save(user);

        return new UserSerialized(updated);
    }

    public void deleteUser(String id) {
        UserEntity user = userRepository.findOneOrFail(id);
        userRepository.delete(user);
    }
}
