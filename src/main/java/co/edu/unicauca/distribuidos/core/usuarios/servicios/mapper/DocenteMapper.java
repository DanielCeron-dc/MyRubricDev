package co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;

public class DocenteMapper {
    
    public DocenteDTO toDto(DocenteEntity docente) {
        DocenteDTO docenteDTO = new DocenteDTO();
        docenteDTO.setId(docente.getId());
        docenteDTO.setNombre(docente.getNombre());
        docenteDTO.setApellido(docente.getApellido());
        docenteDTO.setCorreoAcademico(docente.getCorreoAcademico());
        docenteDTO.setTipoIdentificacion(docente.getTipoIdentificacion());
        docenteDTO.setIdentificacion(docente.getIdentificacion());
        docenteDTO.setTipoDocente(docente.getTipoDocente());
        docenteDTO.setTituloAcademico(docente.getTituloAcademico());
        docenteDTO.setActivo(docente.getActivo());
        return docenteDTO;
    }


    public DocenteEntity toEntity(CrearDocenteDTO docenteDTO) {
        DocenteEntity docenteEntity = new DocenteEntity();
        docenteEntity.setNombre(docenteDTO.getNombre());
        docenteEntity.setApellido(docenteDTO.getApellido());
        docenteEntity.setCorreoAcademico(docenteDTO.getCorreoAcademico());
        docenteEntity.setTipoIdentificacion(docenteDTO.getTipoIdentificacion());
        docenteEntity.setIdentificacion(docenteDTO.getIdentificacion());
        docenteEntity.setTipoDocente(docenteDTO.getTipoDocente());
        docenteEntity.setTituloAcademico(docenteDTO.getTituloAcademico());
        docenteEntity.setActivo(docenteDTO.getActivo());
        return docenteEntity;
    }
}
