package co.edu.unicauca.distribuidos.core.usuarios.servicios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {
    private String message;
    private UsuarioDTO user;
}
