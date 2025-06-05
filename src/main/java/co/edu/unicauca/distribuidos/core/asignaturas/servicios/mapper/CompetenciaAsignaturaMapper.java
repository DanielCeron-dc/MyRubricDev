package co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.CompetenciaAsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import org.springframework.stereotype.Component;

@Component
public class CompetenciaAsignaturaMapper {
    // RequestDTO -> Entity
    public CompetenciaAsignaturaEntity toEntity(CompetenciaAsignaturaRequestDTO dto, CompetenciaProgramaEntity competenciaProgramaEntity) {
        if (dto == null || competenciaProgramaEntity == null) return null;

        CompetenciaAsignaturaEntity entity = new CompetenciaAsignaturaEntity();
        entity.setId(dto.getId());
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCompetenciaPrograma(competenciaProgramaEntity);
        return entity;
    }

    // Entity -> DTO
    public CompetenciaAsignaturaDTO toDTO(CompetenciaAsignaturaEntity entity) {
        if (entity == null || entity.getCompetenciaPrograma() == null) return null;

        CompetenciaAsignaturaDTO dto = new CompetenciaAsignaturaDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setCompetenciaProgramaId(entity.getCompetenciaPrograma().getId());
        return dto;
    }
}

