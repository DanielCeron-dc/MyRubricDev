package co.edu.unicauca.distribuidos.core.asignaturas.repositorios;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;

@Repository
public interface AsignaturaRepository extends JpaRepository<AsignaturaEntity, Integer> {
    // Verificar si existe una asignatura por código
    boolean existsByCodigo(String codigo);
    
    // Buscar asignaturas por semestre
    List<AsignaturaEntity> findBySemestre(Integer semestre);

    // buscar asignatiras por id
    Optional<AsignaturaEntity> findById(Integer id);
    
    // Obtener asignatura con sus relaciones
    @EntityGraph(attributePaths = {"competencias", "resultados", "asignacionesDocente"})
    Optional<AsignaturaEntity> findWithRelationsById(Integer id);
}
