package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignaturaRequest;


public interface AsignaturaService {
    AsignaturaEntity crearAsignatura(AsignaturaRequest request);
    AsignaturaEntity actualizarAsignatura(AsignaturaRequest request);
}
