package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAsignaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadosAsignaturaRepository extends JpaRepository<ResultadoAsignaturaEntity, Integer> {

    List<ResultadoAsignaturaEntity> findByCompetenciaId(Integer idCompetencia);
}
