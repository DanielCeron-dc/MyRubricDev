package co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.ResultadoAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RAActualizarRequestTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RACrearRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ResultadoAsignaturaMapper {
    // RequestDTO -> Entity
    public ResultadoAsignaturaEntity toEntity(RACrearRequestDTO dto, CompetenciaAsignaturaEntity competenciaEntity) {
        if (dto == null || competenciaEntity == null) return null;

        ResultadoAsignaturaEntity entity = new ResultadoAsignaturaEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());

        entity.setCompetencia(competenciaEntity);
        return entity;
    }

    public ResultadoAsignaturaEntity toEntity(RAActualizarRequestTO dto, CompetenciaAsignaturaEntity competenciaEntity) {
        if (dto == null) return null;


        ResultadoAsignaturaEntity entity = new ResultadoAsignaturaEntity();

        entity.setId(dto.getId());
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());

        if (competenciaEntity == null) {
            competenciaEntity = new CompetenciaAsignaturaEntity();
            competenciaEntity.setId(dto.getIdCompetencia());
        }

        entity.setCompetencia(competenciaEntity);
        return entity;
    }

    // Entity -> DTO
    public ResultadoAsignaturaDTO toDTO(ResultadoAsignaturaEntity entity, CompetenciaAsignaturaDTO competenciaDTO) {
        if (entity == null || competenciaDTO == null) return null;

        ResultadoAsignaturaDTO dto = new ResultadoAsignaturaDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setCompetenciaAsignatura(competenciaDTO);
        return dto;
    }
}
