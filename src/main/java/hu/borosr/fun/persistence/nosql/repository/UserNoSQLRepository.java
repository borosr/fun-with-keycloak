package hu.borosr.fun.persistence.nosql.repository;

import hu.borosr.fun.persistence.nosql.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnExpression("${app.mongodb.enabled:false}")
public interface UserNoSQLRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByUsername(String username);
}
