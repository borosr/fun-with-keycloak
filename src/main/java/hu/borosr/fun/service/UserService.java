package hu.borosr.fun.service;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.persistence.Role;
import hu.borosr.fun.persistence.common.repository.UserRepository;
import hu.borosr.fun.persistence.sql.entity.User;
import hu.borosr.fun.exception.ValidationException;
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

    public UserDTO create(@NonNull UserDTO userDTO, @NonNull Role role) throws ValidationException {
        keycloakClientService.signUp(userDTO.getUsername(), userDTO.getPassword(), role);
        return userRepository.save(userDTO);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDTO.builder().username(user.getUsername()).fullName(user.getFullName()).build())
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findFirstByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }
}
