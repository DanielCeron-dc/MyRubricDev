package co.edu.unicauca.distribuidos.core.programa.servicios.mapper;

import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.NivelCompetencia;
import org.springframework.stereotype.Component;

import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.ResultadoAprendizajeProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.servicios.dto.*;

@Component
public class ProgramaMapper {

    public CompetenciaProgramaDTO toDTO(CompetenciaProgramaEntity entity) {
        CompetenciaProgramaDTO dto = new CompetenciaProgramaDTO();
        try {
            dto.setId(entity.getId());
            dto.setCodigo(entity.getCodigo());
            dto.setDescripcion(entity.getDescripcion());
            dto.setNivel(entity.getNivel().name());
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error mapeando entidad ID=" + entity.getId() +
                    ". Detalle: descripción='" + entity.getDescripcion() + "'");
            ex.printStackTrace();
            throw ex; // o envuelve en una excepción personalizada
        }

        return dto;
    }

    public CompetenciaProgramaEntity toEntity(CrearEditarCompetenciaProgramaDTO dto) {
        CompetenciaProgramaEntity entity = new CompetenciaProgramaEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setNivel(NivelCompetencia.valueOf(dto.getNivel()));
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