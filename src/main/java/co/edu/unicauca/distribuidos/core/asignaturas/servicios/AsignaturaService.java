package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.AsignaturaRequestDTO;

import java.util.List;


public interface AsignaturaService {
    AsignaturaDTO crearAsignatura(AsignaturaRequestDTO request);
    AsignaturaDTO actualizarAsignatura(AsignaturaRequestDTO request);
    List<AsignaturaDTO> listarAsignaturas();
}
