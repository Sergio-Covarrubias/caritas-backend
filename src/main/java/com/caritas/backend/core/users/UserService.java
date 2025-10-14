package com.caritas.backend.core.users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.users.dtos.UserRequest;
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

    public UserSerialized createUser(UserRequest request) {
        UserEntity user = new UserEntity(request.id(), request.firstName(), request.lastName(), request.email(),
                request.phoneNumber());
        UserEntity saved = userRepository.save(user);

        return new UserSerialized(saved);
    }

    public UserSerialized updateUser(String id, UserRequest request) {
        UserEntity user = userRepository.findOneOrFail(id);

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());

        UserEntity updated = userRepository.save(user);

        return new UserSerialized(updated);
    }

    public void deleteUser(String id) {
        UserEntity user = userRepository.findOneOrFail(id);
        userRepository.delete(user);
    }
}
