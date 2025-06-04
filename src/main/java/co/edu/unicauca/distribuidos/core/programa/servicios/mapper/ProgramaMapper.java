package co.edu.unicauca.distribuidos.core.programa.servicios.mapper;

import org.springframework.stereotype.Component;

import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.ResultadoAprendizajeProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.servicios.dto.*;

@Component
public class ProgramaMapper {

    public CompetenciaProgramaDTO toDTO(CompetenciaProgramaEntity entity) {
        CompetenciaProgramaDTO dto = new CompetenciaProgramaDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setNivel(entity.getNivel().name());
        return dto;
    }

    public CompetenciaProgramaEntity toEntity(CrearEditarCompetenciaProgramaDTO dto) {
        CompetenciaProgramaEntity entity = new CompetenciaProgramaEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        dto.setNivel(entity.getNivel().name());
        return entity;
    }

    public ResultadoProgramaDTO toDTO(ResultadoAprendizajeProgramaEntity entity) {
        ResultadoProgramaDTO dto = new ResultadoProgramaDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setIdCompetencia(entity.getCompetenciaPrograma().getId());
        return dto;
    }

    public ResultadoAprendizajeProgramaEntity toEntity(CrearEditarResultadoProgramaDTO dto, CompetenciaProgramaEntity competencia) {
        ResultadoAprendizajeProgramaEntity entity = new ResultadoAprendizajeProgramaEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCompetenciaPrograma(competencia);
        return entity;
    }
}