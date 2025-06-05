package co.edu.unicauca.distribuidos.core.rubrica.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos.Rubrica;

/**
 * Repositorio para la entidad Rubrica
 */
@Repository
public interface RubricaRepository extends JpaRepository<Rubrica, Integer> {
    
    /**
     * Busca rúbricas por su estado
     * 
     * @param estado Estado de las rúbricas a buscar (ej. "ACTIVO", "INACTIVO")
     * @return Lista de rúbricas que coinciden con el estado especificado
     */
    List<Rubrica> findByEstado(String estado);
    
    /**
     * Verifica si existe una rúbrica con el nombre especificado (sin distinguir mayúsculas/minúsculas)
     * 
     * @param nombre Nombre de la rúbrica a buscar
     * @return true si existe una rúbrica con ese nombre, false en caso contrario
     */
    boolean existsByNombreIgnoreCase(String nombre);
}

