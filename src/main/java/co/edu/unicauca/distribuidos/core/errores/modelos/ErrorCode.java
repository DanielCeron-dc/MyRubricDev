// ErrorCode.java - Centralized error codes
package co.edu.unicauca.distribuidos.core.errores.modelos;

public enum ErrorCode {
    // Validation errors
    VALIDATION_ERROR("VALIDATION_001", "Validation failed"),
    FIELD_VALIDATION_ERROR("VALIDATION_002", "Field validation error"),
    CONSTRAINT_VIOLATION("VALIDATION_003", "Constraint violation"),

    // Request errors
    MALFORMED_REQUEST("REQUEST_001", "Malformed request"),
    METHOD_NOT_ALLOWED("REQUEST_002", "HTTP method not allowed"),
    MISSING_PARAMETER("REQUEST_003", "Required parameter missing"),
    INVALID_FORMAT("REQUEST_004", "Invalid data format"),

    // Business logic errors
    RESOURCE_NOT_FOUND("BUSINESS_001", "Resource not found"),
    RESOURCE_ALREADY_EXISTS("BUSINESS_002", "Resource already exists"),
    OPERATION_NOT_PERMITTED("BUSINESS_003", "Operation not permitted"),
    BUSINESS_RULE_VIOLATION("BUSINESS_004", "Business rule violation"),
    INSUFFICIENT_PERMISSIONS("BUSINESS_005", "Insufficient permissions"),

    // System errors
    INTERNAL_SERVER_ERROR("SYSTEM_001", "Internal server error"),
    DATABASE_ERROR("SYSTEM_002", "Database operation failed"),
    EXTERNAL_SERVICE_ERROR("SYSTEM_003", "External service unavailable"),
    CONFIGURATION_ERROR("SYSTEM_004", "Configuration error"),

    // Authentication/Authorization errors
    UNAUTHORIZED("AUTH_001", "Unauthorized access"),
    FORBIDDEN("AUTH_002", "Access forbidden"),
    TOKEN_EXPIRED("AUTH_003", "Authentication token expired"),
    INVALID_CREDENTIALS("AUTH_004", "Invalid credentials"),

    // Authentication/Authorization errors (add to existing AUTH section)
    AUTH_INVALID_TOKEN("AUTH_005", "Invalid authentication token"),
    AUTH_TOKEN_MALFORMED("AUTH_006", "Malformed authentication token"),
    AUTH_USER_DISABLED("AUTH_007", "User account is disabled"),
    AUTH_USER_LOCKED("AUTH_008", "User account is locked"),

    // Registration specific errors
    REGISTRATION_USERNAME_EXISTS("REG_001", "Username already exists"),
    REGISTRATION_EMAIL_EXISTS("REG_002", "Email already exists"),
    REGISTRATION_IDENTIFICATION_EXISTS("REG_003", "Identification already exists"),
    REGISTRATION_INVALID_DATA("REG_004", "Invalid registration data"),
    REGISTRATION_FAILED("REG_005", "User registration failed");
    
    

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
