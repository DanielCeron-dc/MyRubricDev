package co.edu.unicauca.distribuidos.core.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication responses containing JWT token
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    
    private String token;
    private String tokenType = "Bearer";
    private long expiresIn;
    private String username;
    
    // Constructor with token and expiration
    public AuthResponseDTO(String token, long expiresIn, String username) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
        this.tokenType = "Bearer";
    }
}

