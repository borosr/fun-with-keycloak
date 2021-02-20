package hu.borosr.fun.persistence.sql.config;

import hu.borosr.fun.persistence.sql.entity.User;
import hu.borosr.fun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
@ConditionalOnExpression("!${app.mongodb.enabled:false}")
public class SQLAuditConfiguration {
    private final UserService userService;

    @Bean
    public AuditorAware<User> auditorAware() {
        return new SQLAuditorAware(userService);
    }

    @RequiredArgsConstructor
    private static class SQLAuditorAware implements AuditorAware<User>  {
        private final UserService userService;
        @Override
        public Optional<User> getCurrentAuditor() {
            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(authentication -> (KeycloakPrincipal) authentication.getPrincipal())
                    .map(KeycloakPrincipal::getKeycloakSecurityContext)
                    .map(KeycloakSecurityContext::getToken)
                    .map(IDToken::getPreferredUsername)
                    .flatMap(userService::findFirstByUsername)
                    .map(User::fromDto);
        }
    }
}
