package co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper;

import co.edu.unicauca.distribuidos.core.asignaturas.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.CompetenciaAsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import org.springframework.stereotype.Component;

@Component
public class CompetenciaAsignaturaMapper {

    public CompetenciaAsignaturaEntity toEntity(CompetenciaAsignaturaRequestDTO dto) {
        CompetenciaAsignaturaEntity entity = new CompetenciaAsignaturaEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        // Crea instancias mínimas de las entidades relacionadas
        AsignaturaEntity asignatura = new AsignaturaEntity();
        asignatura.setId(dto.getAsignaturaId());

        CompetenciaProgramaEntity competencia = new CompetenciaProgramaEntity();
        competencia.setId(dto.getCompetenciaProgramaId());

        competencia.setDescripcion(dto.getDescripcion());
        competencia.setCodigo(dto.getCodigo());


        // Asignar las relaciones
        entity.setAsignatura(asignatura);
        entity.setCompetenciaPrograma(competencia);

        return entity;
    }

    public CompetenciaAsignaturaDTO toDTO(CompetenciaAsignaturaEntity entity) {
        CompetenciaAsignaturaDTO dto = new CompetenciaAsignaturaDTO();
        dto.setId(entity.getId());

        if (entity.getAsignatura() != null) {
            dto.setAsignaturaId(entity.getAsignatura().getId());
        }

        if (entity.getCompetenciaPrograma() != null) {
            dto.setCompetenciaProgramaId(entity.getCompetenciaPrograma().getId());
        }

        return dto;
    }
}

