package hu.borosr.fun.service;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.entity.Role;
import hu.borosr.fun.entity.User;
import hu.borosr.fun.exception.ValidationException;
import hu.borosr.fun.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakClientService keycloakClientService;

    public User create(@NonNull UserDTO userDTO, @NonNull Role role) throws ValidationException {
        keycloakClientService.signUp(userDTO.getUsername(), userDTO.getPassword(), role);
        return userRepository.save(User.builder().username(userDTO.getUsername()).fullName(userDTO.getFullName()).build());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDTO.builder().username(user.getUsername()).fullName(user.getFullName()).build())
                .collect(Collectors.toList());
    }
}
