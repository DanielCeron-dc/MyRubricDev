package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios;
import java.util.List;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;

@Repository
public interface CompetenciaAsignaturaRepository extends JpaRepository<CompetenciaAsignaturaEntity, Integer> {

    // Buscar competencias por programa
    List<CompetenciaAsignaturaEntity> findByCompetenciaPrograma(CompetenciaProgramaEntity programa);

    boolean existsByCodigo(@Size(max = 20) @NotNull String codigo);
}
