// ErrorResponse.java - Standardized error response structure
package co.edu.unicauca.distribuidos.core.errores.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ErrorResponse {
    // Getters and setters
    private boolean success;
    private String errorCode;
    private String message;
    private String details;
    private int httpStatus;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private Map<String, String> fieldErrors;
    private List<String> validationErrors;

    public ErrorResponse() {
        this.success = false;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String errorCode, String message, int httpStatus) {
        this();
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
