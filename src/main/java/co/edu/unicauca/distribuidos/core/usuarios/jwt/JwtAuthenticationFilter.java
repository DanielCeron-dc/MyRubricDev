package co.edu.unicauca.distribuidos.core.usuarios.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Simplified JWT Filter for annotation-based security
 * Only skips truly public endpoints - authorization handled by method annotations
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // Only infrastructure endpoints that should never require auth
    private static final String[] INFRASTRUCTURE_PUBLIC_PATTERNS = {
        "/h2-console/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/actuator/health",
        "/actuator/info",
        "/error",
        "/favicon.ico"
    };

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Only skip infrastructure endpoints - let Spring Security handle @PermitAll
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        
        // Skip CORS preflight
        if ("OPTIONS".equals(method)) {
            return true;
        }

        if (path.startsWith("/api/auth")) {
            log.info("AUTH PATH: Skipping JWT filter for path: {}", path);
            return true;
        }
        
        // Only skip infrastructure endpoints
        for (String pattern : INFRASTRUCTURE_PUBLIC_PATTERNS) {
            if (pathMatcher.match(pattern, path)) {
                log.debug("Skipping JWT filter for infrastructure endpoint: {}", path);
                return true;
            }
        }
        
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // Extract and validate JWT token
            String jwt = extractJwtToken(request);
            
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                setAuthentication(request, jwt);
            }
            // If no valid token, continue anyway - @PreAuthorize/@PermitAll will handle authorization
            
        } catch (Exception e) {
            log.error("JWT authentication error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void setAuthentication(HttpServletRequest request, String jwt) {
        String username = tokenProvider.getUsernameFromToken(jwt);
        List<SimpleGrantedAuthority> authorities = tokenProvider.getAuthoritiesFromToken(jwt);
        
        if (username != null) {
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(username, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Set authentication for user: {} with authorities: {}", username, authorities);
        }
    }
}