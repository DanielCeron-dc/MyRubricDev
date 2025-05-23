package co.edu.unicauca.distribuidos.core.security.dto;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
    
    private Rol rol;
}

