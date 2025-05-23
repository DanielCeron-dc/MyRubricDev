package co.edu.unicauca.distribuidos.core.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter for processing JWT tokens in incoming requests
 * Extracts token from Authorization header, validates it, and sets up authentication in SecurityContext
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // Use constructor injection with @Lazy for UserDetailsService
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, @Lazy UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Determine if the current request path should be skipped by this filter
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        
        // Exact path checks for auth endpoints
        if (path.equals("/auth/register") || path.equals("/auth/login")) {
            log.info("DIRECT MATCH: Skipping JWT filter for auth endpoint: {}", path);
            return true;
        }
        
        // Pattern matching for other public paths
        if (path.startsWith("/auth/") || path.equals("/auth")) {
            log.info("AUTH PATH: Skipping JWT filter for path: {}", path);
            return true;
        }
        
        // Public endpoints check
        if (isPublicEndpoint(request)) {
            log.info("PUBLIC ENDPOINT: Skipping JWT filter for path: {}", path);
            return true;
        }
        
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String method = request.getMethod();
        
        // CRITICAL DEBUGGING - Log extensive details about every request
        log.info("********** JWT FILTER PROCESSING **********");
        log.info("Authenticating request - Path: {}, Method: {}", path, method);
        log.info("Auth Header: {}", request.getHeader("Authorization"));
        log.info("**********************************************");
        
        try {
            // Extract JWT token from the request
            String jwt = getJwtFromRequest(request);
            log.debug("JWT token present: {}", StringUtils.hasText(jwt));

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String username = tokenProvider.getUsernameFromToken(jwt);
                List<SimpleGrantedAuthority> authorities = tokenProvider.getAuthoritiesFromToken(jwt);
                
                log.debug("Username: {}, Authorities: {}", username, authorities);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Successfully set authentication in SecurityContext");
            } else {
                log.debug("No valid JWT token found, continuing filter chain");
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
        }
        
        log.debug("Continuing filter chain after authentication processing");

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from request's Authorization header
     * Format: "Bearer <token>"
     *
     * @param request the HTTP request
     * @return the JWT token or null if not found
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Helper method to get all headers for logging
     */
    private String getHeadersInfo(HttpServletRequest request) {
        StringBuilder headerInfo = new StringBuilder();
        java.util.Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerInfo.append(headerName).append(": ").append(headerValue).append(", ");
        }
        return headerInfo.toString();
    }

    /**
     * Determines if an endpoint is public and should bypass authentication
     * Simplified method to handle non-auth public endpoints
     * 
     * @param request the HTTP request
     * @return true if endpoint is public and should bypass auth, false otherwise
     */
    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        
        // Always skip OPTIONS requests (CORS preflight)
        if (method.equals("OPTIONS")) {
            log.info("CORS PREFLIGHT: Skipping authentication for OPTIONS request");
            return true;
        }
        
        // Auth endpoints are already handled in shouldNotFilter, this is for other public endpoints
        
        // STEP 4: Check other public endpoints
        String[] publicPatterns = {
            "/h2-console/**",     // H2 console
            "/swagger-ui/**",     // Swagger UI
            "/v3/api-docs/**"     // OpenAPI docs
        };
        
        // Check if the path matches any of the public patterns
        for (String pattern : publicPatterns) {
            if (pathMatcher.match(pattern, path)) {
                log.info("Public endpoint detected via pattern matching: {} matches {}", path, pattern);
                return true;
            }
        }
        
        log.info("Authenticated endpoint: {}", path);
        return false;
    }
}
