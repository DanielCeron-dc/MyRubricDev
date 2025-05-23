package co.edu.unicauca.distribuidos.core.security.config;

import co.edu.unicauca.distribuidos.core.security.jwt.JwtAuthenticationEntryPoint;
import co.edu.unicauca.distribuidos.core.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
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

import java.util.Arrays;
import java.util.Collections;

/**
 * Security configuration with explicit separation of public and secured endpoints
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DaoAuthenticationProvider authenticationProvider;
    
    public SecurityConfig(
            @Lazy JwtAuthenticationEntryPoint unauthorizedHandler,
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
            @Lazy DaoAuthenticationProvider authenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }
    
    /**
     * Basic security filter chain configuration 
     * Simple, direct approach to security configuration
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for all endpoints
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configure CORS - must be early in the chain
            .cors(Customizer.withDefaults())
            
            // Basic authorization configuration - PUBLIC ENDPOINTS FIRST
            .authorizeHttpRequests(auth -> auth
                // AUTH ENDPOINTS - exact paths first for clarity
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/**").permitAll()
                
                // OTHER PUBLIC ENDPOINTS
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                
                // PROTECTED ENDPOINTS
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/coordinador/**").hasAuthority("ROLE_COORDINADOR")
                .requestMatchers("/api/docente/**").hasAnyAuthority("ROLE_DOCENTE", "ROLE_COORDINADOR")
                
                // Any other request needs authentication
                .anyRequest().authenticated()
            )
            
            // Configure session management - STATELESS for JWT
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
            // Configure exception handling
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(unauthorizedHandler))
            
            // Configure authentication provider
            .authenticationProvider(authenticationProvider)
            
            // Add JWT filter - after authorization rules
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
            // Allow frames for H2 console
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
            
        return http.build();
    }
    
    /**
     * Configure CORS for the application
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
