package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.ResultadoAprendizajeDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RAActualizarRequestTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RACrearRequestDTO;

/**
 *
 */
public interface RaAsignaturaService {

    /**
     * Crea un resultado de aprendizaje de una asignatura
     *
     * @param request
     * @return
     */
    ResultadoAprendizajeDTO crearRA(RACrearRequestDTO request);

    /**
     * Modifica un resultado de aprendizaje de asignatura
     *
     * @param request
     * @return
     */
    ResultadoAprendizajeDTO actualizarRA(RAActualizarRequestTO request);
}