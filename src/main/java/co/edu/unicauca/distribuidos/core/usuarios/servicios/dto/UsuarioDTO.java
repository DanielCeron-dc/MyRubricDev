package co.edu.unicauca.distribuidos.core.usuarios.servicios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private String username;
    private String rol;
}
