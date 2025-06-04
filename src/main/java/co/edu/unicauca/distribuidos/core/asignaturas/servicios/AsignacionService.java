package co.edu.unicauca.distribuidos.core.asignaturas.servicios;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.AsignacionDocenteCompetenciaDTO;
import org.springframework.transaction.annotation.Transactional;


public interface AsignacionService {

    @Transactional
    AsignacionDocenteCompetenciaDTO asignarDocenteCompetencia(Integer docenteId, Integer asignaturaId, Integer competenciaId);
}
