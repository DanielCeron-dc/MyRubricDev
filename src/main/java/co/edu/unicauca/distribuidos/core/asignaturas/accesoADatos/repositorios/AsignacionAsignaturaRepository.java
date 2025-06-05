package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionAsignaturaEntity;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionAsignaturaRepository extends JpaRepository<AsignacionAsignaturaEntity, Integer> {
    boolean existsByDocenteIdAndCompetenciaIdAndPeriodo(Integer idDocente_id, Integer idCompetencia_id, @Size(max = 100) String periodo);
    boolean existsByDocenteIdAndAsignaturaIdAndPeriodo(Integer idDocente_id, Integer idCompetencia_id, @Size(max = 100) String periodo);


    List<AsignacionAsignaturaEntity> findByDocenteId(Integer id);
}
