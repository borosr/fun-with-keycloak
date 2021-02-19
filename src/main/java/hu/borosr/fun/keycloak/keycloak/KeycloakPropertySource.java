package hu.borosr.fun.keycloak.keycloak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakPropertySource {
    @Value("${keycloak.realm}")
    public String REALM;
    @Value("${keycloak.auth-server-url}")
    public String SERVER_URL;
    @Value("${keycloak.resource}")
    public String CLIENT_ID;
    @Value("${keycloak.credentials.secret}")
    public String CLIENT_SECRET;
}
