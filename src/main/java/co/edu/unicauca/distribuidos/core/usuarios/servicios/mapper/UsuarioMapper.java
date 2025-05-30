package co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.UsuarioDTO;

public class UsuarioMapper {

    public UsuarioDTO toDTO(UsuarioEntity user) {
        return new UsuarioDTO(user.getUsername(), user.getRol().name());
    }
}
