package hu.borosr.fun.controller;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.entity.Role;
import hu.borosr.fun.exception.ValidationException;
import hu.borosr.fun.service.KeycloakClientService;
import hu.borosr.fun.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakClientService keycloakClientService;
    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PermitAll
    public void create(@RequestBody UserDTO userDTO) throws ValidationException {
        userService.create(userDTO, Role.USER);
    }

    @PostMapping("/login")
    @PermitAll
    public AccessTokenResponse login(@RequestBody UserDTO userDTO) {
        return keycloakClientService.login(userDTO.getUsername(), userDTO.getPassword());
    }

    @GetMapping("/users")
    @RolesAllowed("ADMIN")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

}
