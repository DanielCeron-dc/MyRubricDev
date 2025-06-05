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
 * Enhanced Entry point that uses JWT error information from the filter
 * Provides detailed error responses based on the specific JWT error that occurred
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // Request attribute names used by JwtAuthenticationFilter
    private static final String JWT_ERROR_ATTRIBUTE = "JWT_ERROR";
    private static final String JWT_ERROR_TYPE_ATTRIBUTE = "JWT_ERROR_TYPE";

    public JwtAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.error("Unauthorized access attempt to {}: {}", request.getRequestURI(), authException.getMessage());

        // Check if we have JWT error information from the filter
        JwtAuthenticationFilter.JwtErrorInfo jwtError =
                (JwtAuthenticationFilter.JwtErrorInfo) request.getAttribute(JWT_ERROR_ATTRIBUTE);
        String jwtErrorType = (String) request.getAttribute(JWT_ERROR_TYPE_ATTRIBUTE);

        // Determine error code and details
        ErrorCode errorCode;
        String details;
        Map<String, Object> additionalInfo = new HashMap<>();

        if (jwtError != null) {
            log.debug("Using JWT error information: {}", jwtError);
            errorCode = mapJwtErrorToErrorCode(jwtErrorType);
            details = mapJwtErrorToDetails(jwtErrorType, jwtError.getMessage());

            // Add additional context information
            additionalInfo.put("jwtErrorType", jwtErrorType);
            additionalInfo.put("timestamp", jwtError.getTimestamp());

            // Add specific error context based on error type
            addErrorSpecificContext(jwtErrorType, jwtError, additionalInfo);

        } else {
            // Fallback to original logic if no JWT error info available
            errorCode = determineErrorCode(authException);
            details = determineErrorDetails(authException, request);
        }

        // Create comprehensive error response
        ErrorResponse errorResponse = createErrorResponse(errorCode, details, request, additionalInfo);

        // Set response headers
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");

        // Add custom headers for debugging (in development)
        if (jwtErrorType != null) {
            response.setHeader("X-JWT-Error-Type", jwtErrorType);
        }

        // Write error response
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    /**
     * Map JWT error types to appropriate ErrorCode
     */
    private ErrorCode mapJwtErrorToErrorCode(String jwtErrorType) {
        if (jwtErrorType == null) {
            return ErrorCode.UNAUTHORIZED;
        }

        switch (jwtErrorType) {
            case "EXPIRED":
                return ErrorCode.TOKEN_EXPIRED;
            case "MALFORMED":
            case "INVALID_SIGNATURE":
            case "UNSUPPORTED":
                return ErrorCode.INVALID_CREDENTIALS;
            case "EMPTY_TOKEN":
            case "VALIDATION_FAILED":
                return ErrorCode.UNAUTHORIZED;
            case "UNEXPECTED_ERROR":
            default:
                return ErrorCode.UNAUTHORIZED;
        }
    }

    /**
     * Map JWT error types to user-friendly details
     */
    private String mapJwtErrorToDetails(String jwtErrorType, String originalMessage) {
        if (jwtErrorType == null) {
            return "No tienes permisos para acceder a este recurso";
        }

        switch (jwtErrorType) {
            case "EXPIRED":
                return "El token de autenticación ha expirado. Por favor, inicia sesión de nuevo";
            case "MALFORMED":
                return "El token de autenticación está malformado";
            case "INVALID_SIGNATURE":
                return "La firma del token de autenticación es inválida";
            case "UNSUPPORTED":
                return "El formato del token de autenticación no es compatible";
            case "EMPTY_TOKEN":
                return "Se requiere un token de autenticación válido";
            case "VALIDATION_FAILED":
                return "El token de autenticación no es válido";
            case "UNEXPECTED_ERROR":
                return "Ocurrió un error inesperado durante la autenticación";
            default:
                return "Error de autenticación: " + originalMessage;
        }
    }

    /**
     * Add error-specific context information
     */
    private void addErrorSpecificContext(String jwtErrorType,
                                         JwtAuthenticationFilter.JwtErrorInfo jwtError,
                                         Map<String, Object> additionalInfo) {
        switch (jwtErrorType) {
            case "EXPIRED":
                additionalInfo.put("action", "login_required");
                additionalInfo.put("reason", "token_expired");
                break;
            case "MALFORMED":
            case "UNSUPPORTED":
                additionalInfo.put("action", "check_token_format");
                additionalInfo.put("reason", "invalid_token_format");
                break;
            case "INVALID_SIGNATURE":
                additionalInfo.put("action", "obtain_new_token");
                additionalInfo.put("reason", "signature_verification_failed");
                break;
            case "EMPTY_TOKEN":
                additionalInfo.put("action", "provide_authorization_header");
                additionalInfo.put("reason", "missing_token");
                break;
            case "VALIDATION_FAILED":
                additionalInfo.put("action", "verify_token_validity");
                additionalInfo.put("reason", "token_validation_failed");
                break;
        }
    }

    /**
     * Create a comprehensive error response
     */
    private ErrorResponse createErrorResponse(ErrorCode errorCode, String details,
                                              HttpServletRequest request,
                                              Map<String, Object> additionalInfo) {
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                HttpServletResponse.SC_UNAUTHORIZED
        );

        errorResponse.setDetails(details);
        errorResponse.setPath(request.getRequestURI());

        // Add additional context if available
        if (!additionalInfo.isEmpty()) {
            // You might want to add a method to ErrorResponse to handle additional info
            // For now, we'll add it to details if ErrorResponse doesn't support it
            log.debug("Additional error context: {}", additionalInfo);
        }

        return errorResponse;
    }

    /**
     * Fallback method - original error determination logic
     */
    private ErrorCode determineErrorCode(AuthenticationException authException) {
        String exceptionName = authException.getClass().getSimpleName();
        String message = authException.getMessage();

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
     * Fallback method - original error details determination logic
     */
    private String determineErrorDetails(AuthenticationException authException, HttpServletRequest request) {
        String exceptionName = authException.getClass().getSimpleName();
        String message = authException.getMessage();

        switch (exceptionName) {
            case "BadCredentialsException":
                return "Las credenciales proporcionadas no son válidas";
            case "AccountExpiredException":
                return "La cuenta del usuario ha expirado";
            case "CredentialsExpiredException":
                return "Las credenciales de autenticación han expirado";
            case "DisabledException":
                return "La cuenta del usuario está desactivada";
            case "LockedException":
                return "La cuenta del usuario está bloqueada";
            default:
                if (message != null && message.contains("JWT")) {
                    if (message.contains("expired")) {
                        return "El token ha expirado. Por favor, inicia sesión de nuevo";
                    } else if (message.contains("malformed")) {
                        return "El token está malformado";
                    } else {
                        return "Se ha proporcionado un token inválido";
                    }
                }
                return "No tienes permisos para acceder a este recurso";
        }
    }
}