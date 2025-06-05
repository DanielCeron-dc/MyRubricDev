package co.edu.unicauca.distribuidos.core.asignaturas.servicios;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignacionAsignaturaDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface AsignacionService {

    @Transactional
    AsignacionAsignaturaDTO asignarDocenteCompetencia(Integer docenteId, Integer asignaturaId, Integer competenciaId);

    List<AsignacionAsignaturaDTO> listarAsignaciones();

    List<AsignacionAsignaturaDTO> listarAsignacionesAsociadas();

    AsignacionAsignaturaDTO buscarAsignacionPorId(Integer id);
}
