package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAprendizajeAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RAActualizarRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RACrearRequest;

public interface ResultadoAprendizajeService {
    ResultadoAprendizajeAsignaturaEntity crearRA(RACrearRequest request);
    ResultadoAprendizajeAsignaturaEntity actualizarRA(RAActualizarRequest request);
}