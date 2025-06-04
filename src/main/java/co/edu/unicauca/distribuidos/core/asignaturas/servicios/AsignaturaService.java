package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignaturaRequestDTO;


public interface AsignaturaService {
    AsignaturaEntity crearAsignatura(AsignaturaRequestDTO request);
    AsignaturaEntity actualizarAsignatura(AsignaturaRequestDTO request);
}
