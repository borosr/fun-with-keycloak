package hu.borosr.fun.keycloak;

import hu.borosr.fun.keycloak.keycloak.KeycloakClientPropertySource;
import hu.borosr.fun.keycloak.keycloak.KeycloakPropertySource;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@RequiredArgsConstructor
@Import({KeycloakPropertySource.class, KeycloakClientPropertySource.class})
public class KeycloakClientConfig {

    private final KeycloakPropertySource keycloakPropertySource;
    private final KeycloakClientPropertySource keycloakClientPropertySource;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakPropertySource.SERVER_URL)
                .realm(keycloakClientPropertySource.REALM)
                .username(keycloakClientPropertySource.USERNAME)
                .password(keycloakClientPropertySource.PASSWORD)
                .clientId(keycloakClientPropertySource.CLIENT_ID)
                .build();
    }

}
