package hu.borosr.fun.persistence.common.repository;

import hu.borosr.fun.persistence.sql.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User product);
    Optional<User> findById(String id);
    Optional<User> findFirstByUsername(String username);
    void deleteById(String id);
    List<User> findAll();
}
