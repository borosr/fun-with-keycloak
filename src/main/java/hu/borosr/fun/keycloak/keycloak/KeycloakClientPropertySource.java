package hu.borosr.fun.keycloak.keycloak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientPropertySource {
    @Value("${keycloakAdmin.realm}")
    public String REALM;
    @Value("${keycloakAdmin.username}")
    public String USERNAME;
    @Value("${keycloakAdmin.password}")
    public String PASSWORD;
    @Value("${keycloakAdmin.resource}")
    public String CLIENT_ID;
}
