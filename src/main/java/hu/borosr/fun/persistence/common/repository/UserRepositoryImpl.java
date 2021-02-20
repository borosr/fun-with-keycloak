package hu.borosr.fun.persistence.common.repository;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.persistence.nosql.repository.UserNoSQLRepository;
import hu.borosr.fun.persistence.sql.entity.User;
import hu.borosr.fun.persistence.sql.repository.UserSQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${app.mongodb.enabled:false}")
    private boolean nosqlEnabled;
    private UserSQLRepository userSQLRepository;
    private UserNoSQLRepository userNoSQLRepository;

    public UserRepositoryImpl(@Autowired(required = false) UserSQLRepository userSQLRepository,
                              @Autowired(required = false) UserNoSQLRepository userNoSQLRepository) {
        this.userSQLRepository = userSQLRepository;
        this.userNoSQLRepository = userNoSQLRepository;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return nosqlEnabled ?
                userNoSQLRepository.save(hu.borosr.fun.persistence.nosql.model.User.fromDto(userDTO)).toDto() :
                userSQLRepository.save(User.fromDto(userDTO)).toDto();
    }

    @Override
    public Optional<UserDTO> findById(String id) {
        return nosqlEnabled ?
                userNoSQLRepository.findById(id).map(hu.borosr.fun.persistence.nosql.model.User::toDto) :
                userSQLRepository.findById(id).map(User::toDto);
    }

    @Override
    public Optional<UserDTO> findFirstByUsername(String username) {
        return nosqlEnabled ?
                userNoSQLRepository.findFirstByUsername(username).map(hu.borosr.fun.persistence.nosql.model.User::toDto) :
                userSQLRepository.findFirstByUsername(username).map(User::toDto);
    }

    @Override
    public void deleteById(String id) {
        if (nosqlEnabled) {
            userNoSQLRepository.deleteById(id);
        } else {
            userSQLRepository.deleteById(id);
        }
    }

    @Override
    public List<UserDTO> findAll() {
        return nosqlEnabled ?
                userNoSQLRepository.findAll().stream()
                        .map(hu.borosr.fun.persistence.nosql.model.User::toDto).collect(Collectors.toList()) :
                userSQLRepository.findAll().stream().map(User::toDto).collect(Collectors.toList());
    }
}
