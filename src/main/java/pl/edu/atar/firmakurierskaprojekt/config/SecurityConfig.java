package pl.edu.atar.firmakurierskaprojekt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfiguruje reguły bezpieczeństwa HTTP aplikacji na potrzeby środowiska deweloperskiego.
 *
 * @author Szymon Pawełczyk
 */
@Configuration
public class SecurityConfig {

    /**
     * Definiuje łańcuch filtrów bezpieczeństwa zezwalający na dostęp do wszystkich zasobów
     * oraz wyłączający ochronę CSRF i blokadę ramek dla konsoli H2.
     *
     * @param http obiekt konfiguracyjny {@link HttpSecurity}
     * @return skonfigurowany obiekt {@link SecurityFilterChain}
     * @throws Exception w przypadku błędu konfiguracji bezpieczeństwa
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
