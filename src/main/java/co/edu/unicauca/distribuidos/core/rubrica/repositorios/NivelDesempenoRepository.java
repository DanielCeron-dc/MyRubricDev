package co.edu.unicauca.distribuidos.core.rubrica.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.rubrica.dominio.NivelDesempeno;

/**
 * Repositorio para la entidad NivelDesempeno
 */
@Repository
public interface NivelDesempenoRepository extends JpaRepository<NivelDesempeno, Integer> {
    
    /**
     * Busca niveles de desempeño por el ID del criterio al que pertenecen
     * 
     * @param criterioId ID del criterio
     * @return Lista de niveles de desempeño asociados al criterio especificado
     */
    List<NivelDesempeno> findByCriterioId(Integer criterioId);
    
    /**
     * Busca niveles de desempeño por el ID de la rúbrica y el ID del criterio
     * 
     * @param rubricaId ID de la rúbrica
     * @param criterioId ID del criterio
     * @return Lista de niveles de desempeño asociados al criterio de la rúbrica especificada
     */
    List<NivelDesempeno> findByCriterioRubricaIdAndCriterioId(Integer rubricaId, Integer criterioId);
}

