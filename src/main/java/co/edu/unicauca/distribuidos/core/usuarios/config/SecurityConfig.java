package co.edu.unicauca.distribuidos.core.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import co.edu.unicauca.distribuidos.core.usuarios.jwt.JwtAuthenticationEntryPoint;
import co.edu.unicauca.distribuidos.core.usuarios.jwt.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Decentralized Security Configuration
 * Most authorization is handled at controller/method level using annotations
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,    // Enable @PreAuthorize, @PostAuthorize
    securedEnabled = true,    // Enable @Secured
    jsr250Enabled = true      // Enable @RolesAllowed
)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DaoAuthenticationProvider authenticationProvider;

    // Only truly public endpoints that never need protection
    private static final String[] ALWAYS_PUBLIC_ENDPOINTS = {
        "/h2-console/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/actuator/health",
        "/actuator/info",
        "/error",
        "/auth/**",
        "/api/auth/**"
    };

    public SecurityConfig(
            @Lazy JwtAuthenticationEntryPoint unauthorizedHandler,
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
            @Lazy DaoAuthenticationProvider authenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_COORDINADOR > ROLE_DOCENTE > ROLE_USER");
        return hierarchy;
    }

    /**
     * Minimal security configuration - most authorization handled by method annotations
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers.frameOptions().disable())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            
            .authorizeHttpRequests(auth -> auth
                // Only configure truly public endpoints here
                .requestMatchers(ALWAYS_PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS preflight
                
                // Everything else requires authentication - authorization handled by method annotations
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(unauthorizedHandler))

            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}