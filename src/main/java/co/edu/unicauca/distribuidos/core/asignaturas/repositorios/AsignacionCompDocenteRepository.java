package co.edu.unicauca.distribuidos.core.asignaturas.repositorios;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionCompDocenteEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;


@Repository
public interface AsignacionCompDocenteRepository extends JpaRepository<AsignacionCompDocenteEntity, Integer> {
    // Verificar si existe una asignación para docente en periodo
    boolean existsByUsuarioAndAsignaturaAndPeriodo(DocenteEntity docente, AsignaturaEntity asignatura, String periodo);
    
    // Verificar asignación por docente, competencia y periodo
    boolean existsByUsuarioAndCompetenciaAsignaturaAndPeriodo(
        DocenteEntity docente, 
        CompetenciaAsignaturaEntity competencia, 
        String periodo
    );
    
    // Buscar asignaciones por docente y periodo
    List<AsignacionCompDocenteEntity> findByUsuarioAndPeriodo(DocenteEntity docente, String periodo);
}