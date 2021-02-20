package hu.borosr.fun.persistence.common.repository;

import hu.borosr.fun.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserDTO save(UserDTO userDTO);
    Optional<UserDTO> findById(String id);
    Optional<UserDTO> findFirstByUsername(String username);
    void deleteById(String id);
    List<UserDTO> findAll();
}
