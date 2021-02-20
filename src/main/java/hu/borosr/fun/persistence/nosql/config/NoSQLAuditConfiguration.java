package hu.borosr.fun.persistence.nosql.config;

import hu.borosr.fun.dto.UserDTO;
import hu.borosr.fun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
@RequiredArgsConstructor
@ConditionalOnExpression("${app.mongodb.enabled:false}")
public class NoSQLAuditConfiguration {
    private final UserService userService;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new MongoAuditorAware(userService);
    }

    @RequiredArgsConstructor
    private static class MongoAuditorAware implements AuditorAware<String>  {
        private final UserService userService;
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(authentication -> (KeycloakPrincipal) authentication.getPrincipal())
                    .map(KeycloakPrincipal::getKeycloakSecurityContext)
                    .map(KeycloakSecurityContext::getToken)
                    .map(IDToken::getPreferredUsername)
                    .flatMap(userService::findFirstByUsername)
                    .map(UserDTO::getUsername);
        }
    }
}
