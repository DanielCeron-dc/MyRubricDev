package co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.AsignaturaRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AsignaturaMapper {

    // De RequestDTO a Entity
    public AsignaturaEntity toEntity(AsignaturaRequestDTO dto) {
        if (dto == null) return null;

        AsignaturaEntity entity = new AsignaturaEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setCreditos(dto.getCreditos());
        entity.setCodigo(dto.getCodigo());
        entity.setObjetivos(dto.getObjetivos());
        entity.setSemestre(dto.getSemestre());
        entity.setActiva(true); // Valor por defecto al crear
        return entity;
    }

    // De Entity a DTO
    public AsignaturaDTO toDTO(AsignaturaEntity entity) {
        if (entity == null) return null;

        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCreditos(entity.getCreditos());
        dto.setCodigo(entity.getCodigo());
        dto.setObjetivos(entity.getObjetivos());
        dto.setSemestre(entity.getSemestre());
        return dto;
    }
}
