package executor.service.config;

import com.google.common.net.HttpHeaders;
import executor.service.security.RsaManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {

    @Value("${client.auth.token.value}")
    private String clientApiToken;

    @Autowired
    private RsaManager rsaManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests.anyRequest().access(hasApiToken()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private AuthorizationManager<RequestAuthorizationContext> hasApiToken() {
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return new AuthorizationDecision(isValidApiToken(tokenHeader));
        };
    }

    private boolean isValidApiToken(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Token ")) {
            String token = tokenHeader.substring(6);
            String decryptedToken = rsaManager.decrypt(token);
            return clientApiToken.equals(decryptedToken);
        }
        return false;
    }
}

