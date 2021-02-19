package hu.borosr.fun.loader;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.entity.Role;
import hu.borosr.fun.entity.User;
import hu.borosr.fun.exception.ValidationException;
import hu.borosr.fun.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public final static String USER = "basic_user";
    public final static String ADMIN = "basic_admin";

    @Override
    public void run(String... args) {
        userService.findByUsername(USER).orElseGet(() -> createUser(USER, Role.USER));
        userService.findByUsername(ADMIN).orElseGet(() -> createUser(ADMIN, Role.ADMIN));
    }

    private User createUser(String username, Role role) {
        try {
            return userService.create(UserDTO.builder()
                    .username(username)
                    .password(username)
                    .fullName(username).build(), role);
        } catch (ValidationException e) {
            log.warn("Missing user: {}, got exception: {}", username, e);
        }
        return null;
    }
}
