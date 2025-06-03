package co.edu.unicauca.distribuidos.core.rubrica.servicios;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearNivelDesempenoDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;

/**
 * Servicio que gestiona las operaciones relacionadas con los niveles de desempeño
 */
public interface NivelDesempenoService {
    
    /**
     * Crea un nuevo nivel de desempeño
     * 
     * @param nivelDTO Datos del nivel de desempeño a crear
     * @return Nivel de desempeño creado con su identificador asignado
     * @throws IllegalArgumentException si el criterio asociado no existe
     */
    NivelDesempenoDTO crearNivelDesempeno(CrearNivelDesempenoDTO nivelDTO);
    
    /**
     * Actualiza un nivel de desempeño existente
     * 
     * @param nivelDTO Datos actualizados del nivel de desempeño
     * @return Nivel de desempeño actualizado
     * @throws IllegalArgumentException si no se encuentra el nivel de desempeño o el criterio asociado
     */
    NivelDesempenoDTO actualizarNivelDesempeno(NivelDesempenoDTO nivelDTO);
}

