package hu.borosr.fun.persistence.sql.repository;

import hu.borosr.fun.persistence.common.repository.UserRepository;
import hu.borosr.fun.persistence.sql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSQLRepository extends UserRepository, JpaRepository<User, String> {
    Optional<User> findFirstByUsername(String username);
}
