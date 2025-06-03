package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.rubrica.dominio.Criterio;

/**
 * Repositorio para la entidad Criterio
 */
@Repository
public interface CriterioRepository extends JpaRepository<Criterio, Integer> {
    
    /**
     * Busca criterios por el ID de la rúbrica a la que pertenecen
     * 
     * @param rubricaId ID de la rúbrica
     * @return Lista de criterios asociados a la rúbrica especificada
     */
    List<Criterio> findByRubricaId(Integer rubricaId);
    
    /**
     * Busca un criterio específico dentro de una rúbrica
     * 
     * @param rubricaId ID de la rúbrica
     * @param criterioId ID del criterio
     * @return Optional con el criterio si existe, Optional vacío en caso contrario
     */
    Optional<Criterio> findByRubricaIdAndId(Integer rubricaId, Integer criterioId);
}

