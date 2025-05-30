package co.edu.unicauca.distribuidos.core.usuarios.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for handling authentication errors
 * Returns a proper error response when users try to access protected resources without authentication
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    
    public JwtAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                         AuthenticationException authException) throws IOException, ServletException {
        
        log.error("Unauthorized access attempt to {}: {}", request.getRequestURI(), authException.getMessage());
        
        // Determine specific error code based on exception type or message
        ErrorCode errorCode = determineErrorCode(authException);
        String details = determineErrorDetails(authException, request);
        
        // Create consistent error response
        ErrorResponse errorResponse = new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            HttpServletResponse.SC_UNAUTHORIZED
        );
        errorResponse.setDetails(details);
        errorResponse.setPath(request.getRequestURI());
        
        // Set response headers
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        
        // Write error response
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
    
    /**
     * Determine the appropriate error code based on the authentication exception
     */

    /**
     * Determine the appropriate error code based on the authentication exception
     */
    private ErrorCode determineErrorCode(AuthenticationException authException) {
        String exceptionName = authException.getClass().getSimpleName();
        String message = authException.getMessage();
        
        // Check for specific authentication scenarios
        if (message != null) {
            if (message.contains("JWT") || message.contains("token")) {
                if (message.contains("expired")) {
                    return ErrorCode.TOKEN_EXPIRED;
                } else if (message.contains("malformed") || message.contains("invalid")) {
                    return ErrorCode.INVALID_CREDENTIALS;
                }
            }
            
            if (message.contains("Full authentication is required")) {
                return ErrorCode.UNAUTHORIZED;
            }
        }
        
        // Check by exception type
        switch (exceptionName) {
            case "BadCredentialsException":
                return ErrorCode.INVALID_CREDENTIALS;
            case "AccountExpiredException":
            case "CredentialsExpiredException":
                return ErrorCode.TOKEN_EXPIRED;
            case "DisabledException":
            case "LockedException":
                return ErrorCode.FORBIDDEN;
            default:
                return ErrorCode.UNAUTHORIZED;
        }
    }


    /**
     * Determine detailed error message based on the authentication exception
     */
    private String determineErrorDetails(AuthenticationException authException, HttpServletRequest request) {
        String exceptionName = authException.getClass().getSimpleName();
        String message = authException.getMessage();
        
        // Provide user-friendly error details
        switch (exceptionName) {
            case "BadCredentialsException":
                return "The provided authentication credentials are invalid";
            case "AccountExpiredException":
                return "The user account has expired";
            case "CredentialsExpiredException":
                return "The authentication credentials have expired";
            case "DisabledException":
                return "The user account is disabled";
            case "LockedException":
                return "The user account is locked";
            default:
                if (message != null && message.contains("JWT")) {
                    if (message.contains("expired")) {
                        return "The authentication token has expired. Please login again";
                    } else if (message.contains("malformed")) {
                        return "The authentication token is malformed";
                    } else {
                        return "Invalid authentication token provided";
                    }
                }
                return "Authentication is required to access this resource";
        }
    }

    
}

