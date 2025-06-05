package co.edu.unicauca.distribuidos.core.usuarios.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
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
 * JWT Filter with comprehensive error handling
 * Captures and logs JWT errors without breaking the filter chain
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

    // Auth endpoints that should never require authentication
    private static final String[] AUTH_PUBLIC_PATTERNS = {
            "/api/auth/**",
            "/auth/**",
            "/api/api/auth/**"
    };

    // Request attribute to store JWT error information
    private static final String JWT_ERROR_ATTRIBUTE = "JWT_ERROR";
    private static final String JWT_ERROR_TYPE_ATTRIBUTE = "JWT_ERROR_TYPE";

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

        // Skip all auth endpoints using pattern matching
        for (String pattern : AUTH_PUBLIC_PATTERNS) {
            if (pathMatcher.match(pattern, path)) {
                log.info("AUTH PATH: Skipping JWT filter for path: {}", path);
                return true;
            }
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

        String jwt = null;
        JwtErrorInfo errorInfo = null;

        try {
            // Extract JWT token
            jwt = extractJwtToken(request);

            if (StringUtils.hasText(jwt)) {
                // Validate and set authentication
                if (tokenProvider.validateToken(jwt)) {
                    setAuthentication(request, jwt);
                    log.debug("Successfully authenticated user from JWT token");
                } else {
                    log.warn("JWT token validation failed for token: {}", maskToken(jwt));
                    errorInfo = new JwtErrorInfo("VALIDATION_FAILED", "Token validation failed", null);
                }
            } else {
                log.debug("No JWT token found in request to: {}", request.getRequestURI());
            }

        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            log.error("JWT token expired for token: {} - Expiration: {}",
                    maskToken(jwt), ex.getClaims().getExpiration(), ex);
            errorInfo = new JwtErrorInfo("EXPIRED", "JWT token has expired", ex);

        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            log.error("Malformed JWT token: {} - Error: {}", maskToken(jwt), ex.getMessage(), ex);
            errorInfo = new JwtErrorInfo("MALFORMED", "JWT token is malformed", ex);

        } catch (io.jsonwebtoken.SignatureException ex) {
            log.error("Invalid JWT signature for token: {} - Error: {}", maskToken(jwt), ex.getMessage(), ex);
            errorInfo = new JwtErrorInfo("INVALID_SIGNATURE", "JWT signature is invalid", ex);

        } catch (io.jsonwebtoken.UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {} - Error: {}", maskToken(jwt), ex.getMessage(), ex);
            errorInfo = new JwtErrorInfo("UNSUPPORTED", "JWT token is unsupported", ex);

        } catch (IllegalArgumentException ex) {
            log.error("JWT token is empty or null - Error: {}", ex.getMessage(), ex);
            errorInfo = new JwtErrorInfo("EMPTY_TOKEN", "JWT token is missing or empty", ex);

        } catch (Exception ex) {
            log.error("Unexpected error during JWT processing for token: {} - Error: {}",
                    maskToken(jwt), ex.getMessage(), ex);
            errorInfo = new JwtErrorInfo("UNEXPECTED_ERROR", "Unexpected error during JWT processing", ex);
        }

        // Store error information in request for potential use by EntryPoint
        if (errorInfo != null) {
            request.setAttribute(JWT_ERROR_ATTRIBUTE, errorInfo);
            request.setAttribute(JWT_ERROR_TYPE_ATTRIBUTE, errorInfo.getErrorType());
            log.debug("Stored JWT error info in request: {}", errorInfo.getErrorType());
        }

        // Continue filter chain - let Spring Security handle authorization
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
        try {
            String username = tokenProvider.getUsernameFromToken(jwt);
            List<SimpleGrantedAuthority> authorities = tokenProvider.getAuthoritiesFromToken(jwt);

            if (username != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Set authentication for user: {} with authorities: {}", username, authorities);
            }
        } catch (Exception ex) {
            log.error("Error setting authentication: {}", ex.getMessage(), ex);
            // Don't throw exception here - let the filter chain continue
        }
    }

    /**
     * Mask JWT token for logging purposes (show only first and last 6 characters)
     */
    private String maskToken(String token) {
        if (token == null || token.length() <= 12) {
            return "***";
        }
        return token.substring(0, 6) + "..." + token.substring(token.length() - 6);
    }

    /**
     * Inner class to store JWT error information
     */
    public static class JwtErrorInfo {
        private final String errorType;
        private final String message;
        private final Exception exception;
        private final long timestamp;

        public JwtErrorInfo(String errorType, String message, Exception exception) {
            this.errorType = errorType;
            this.message = message;
            this.exception = exception;
            this.timestamp = System.currentTimeMillis();
        }

        public String getErrorType() { return errorType; }
        public String getMessage() { return message; }
        public Exception getException() { return exception; }
        public long getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return String.format("JwtErrorInfo{type='%s', message='%s', timestamp=%d}",
                    errorType, message, timestamp);
        }
    }
}