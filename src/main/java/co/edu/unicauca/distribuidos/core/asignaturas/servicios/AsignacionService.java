package co.edu.unicauca.distribuidos.core.asignaturas.servicios;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionCompDocenteEntity;


public interface AsignacionService {
    AsignacionCompDocenteEntity asignarDocenteCompetencia(Long docenteId, Long asignaturaId, Long competenciaId);
}
