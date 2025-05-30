package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;

public interface DocenteService {
    public DocenteDTO save(CrearDocenteDTO docente);
    // TODO: Hacer lo que falte
}
