package co.edu.unicauca.distribuidos.core.usuarios.servicios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication requests (login)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}

