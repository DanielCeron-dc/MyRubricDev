package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;

public interface DocenteService {

    /**
     * Guarda un docente en la base de datos
     * @param docente
     * @return
     */
    public DocenteDTO save(CrearDocenteDTO docente);
    // TODO: Hacer lo que falte
}
