package hu.borosr.fun.service;

import hu.borosr.fun.persistence.sql.entity.Role;
import hu.borosr.fun.exception.ValidationException;
import hu.borosr.fun.keycloak.keycloak.KeycloakPropertySource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClientService {

    private final Keycloak keycloak;
    private final KeycloakPropertySource keycloakPropertySource;

    public void signUp(String username, String password, Role role) throws ValidationException {
        var user = createUser(username, password);
        var realm = keycloak.realm(keycloakPropertySource.REALM);
        var response = realm.users().create(user);
        setRole(realm, response, role);
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            log.error("Error when creating user: {} status: {}", username, response.getStatus());
            throw new ValidationException("error creating user");
        }

    }

    private void setRole(RealmResource realm, Response response, Role role) {
        var clientId = realm.clients().findByClientId(keycloakPropertySource.CLIENT_ID)
                .stream().findFirst().map(ClientRepresentation::getId).orElseThrow();
        realm.users().get(CreatedResponseUtil.getCreatedId(response)).roles().clientLevel(clientId).add(
                List.of(realm.clients().get(clientId).roles().get(role.name()).toRepresentation())
        );
    }

    private UserRepresentation createUser(String username, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(List.of(createCredential(password)));
//        userRepresentation.setClientRoles(Map.of(keycloakPropertySource.CLIENT_ID, List.of(Role.USER.name())));
        return userRepresentation;
    }

    private CredentialRepresentation createCredential(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(password);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }

    public AccessTokenResponse login(String username, String password) {
        return Keycloak.getInstance(
                keycloakPropertySource.SERVER_URL,
                keycloakPropertySource.REALM,
                username, password,
                keycloakPropertySource.CLIENT_ID,
                keycloakPropertySource.CLIENT_SECRET)
                .tokenManager()
                .getAccessToken();
    }
}
