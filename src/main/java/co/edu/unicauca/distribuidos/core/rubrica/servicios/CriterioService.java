package co.edu.unicauca.distribuidos.core.rubrica.servicios;

import java.util.List;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;

/**
 * Servicio que gestiona las operaciones relacionadas con los criterios de evaluación
 */
public interface CriterioService {
    
    /**
     * Crea un nuevo criterio de evaluación
     * 
     * @param criterioDTO Datos del criterio a crear
     * @return Criterio creado con su identificador asignado
     * @throws IllegalArgumentException si la rúbrica asociada no existe
     */
    CriterioDTO crearCriterio(CriterioDTO criterioDTO);
    
    /**
     * Actualiza un criterio de evaluación existente
     * 
     * @param criterioDTO Datos actualizados del criterio
     * @return Criterio actualizado
     * @throws IllegalArgumentException si no se encuentra el criterio o la rúbrica asociada
     */
    CriterioDTO actualizarCriterio(CriterioDTO criterioDTO);
    
    /**
     * Obtiene la lista de niveles de desempeño asociados a un criterio de una rúbrica específica
     * 
     * @param idRubrica Identificador de la rúbrica
     * @param idCriterio Identificador del criterio
     * @return Lista de niveles de desempeño del criterio
     * @throws IllegalArgumentException si no se encuentra la rúbrica o el criterio
     */
    List<NivelDesempenoDTO> listarNivelesCriterio(Integer idRubrica, Integer idCriterio);
}

