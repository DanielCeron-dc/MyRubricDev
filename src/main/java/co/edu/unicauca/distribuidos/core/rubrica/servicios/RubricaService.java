package co.edu.unicauca.distribuidos.core.rubrica.servicios;

import java.util.List;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearRubricaDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.RubricaDTO;

/**
 * Servicio que gestiona las operaciones relacionadas con las rúbricas
 */
public interface RubricaService {
    
    /**
     * Obtiene la lista de todas las rúbricas disponibles
     * 
     * @return Lista de rúbricas
     */
    List<RubricaDTO> listarRubricas();
    
    /**
     * Busca una rúbrica por su identificador
     * 
     * @param id Identificador de la rúbrica
     * @return Rúbrica encontrada
     * @throws IllegalArgumentException si no se encuentra la rúbrica
     */
    RubricaDTO obtenerRubricaPorId(Integer id);
    
    /**
     * Crea una nueva rúbrica
     * 
     * @param rubricaDTO Datos de la rúbrica a crear
     * @return Rúbrica creada con su identificador asignado
     */
    RubricaDTO crearRubrica(CrearRubricaDTO rubricaDTO);
    
    /**
     * Actualiza una rúbrica existente
     * 
     * @param rubricaDTO Datos actualizados de la rúbrica
     * @return Rúbrica actualizada
     * @throws IllegalArgumentException si no se encuentra la rúbrica
     */
    RubricaDTO actualizarRubrica(RubricaDTO rubricaDTO);
    
    /**
     * Obtiene la lista de criterios asociados a una rúbrica
     * 
     * @param id Identificador de la rúbrica
     * @return Lista de criterios de la rúbrica
     * @throws IllegalArgumentException si no se encuentra la rúbrica
     */
    List<CriterioDTO> listarCriteriosRubrica(Integer id);
}
