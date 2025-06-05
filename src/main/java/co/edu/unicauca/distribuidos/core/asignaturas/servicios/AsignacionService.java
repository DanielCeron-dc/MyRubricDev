package co.edu.unicauca.distribuidos.core.asignaturas.servicios;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignacionAsignaturaDTO;
import org.springframework.transaction.annotation.Transactional;


public interface AsignacionService {

    @Transactional
    AsignacionAsignaturaDTO asignarDocenteCompetencia(Integer docenteId, Integer asignaturaId, Integer competenciaId);
}
