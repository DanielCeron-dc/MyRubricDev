package co.edu.unicauca.distribuidos.core.asignaturas.repositorios;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;

@Repository
public interface CompetenciaAsignaturaRepository extends JpaRepository<CompetenciaAsignaturaEntity, Integer> {
    // Buscar competencias por asignatura
    List<CompetenciaAsignaturaEntity> findByAsignatura(AsignaturaEntity asignatura);
    
    // Buscar competencias por programa
    List<CompetenciaAsignaturaEntity> findByCompetenciaPrograma(CompetenciaProgramaEntity programa);
}
