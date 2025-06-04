package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAsignaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadosAsignaturaRepository extends JpaRepository<ResultadoAsignaturaEntity, Integer> {
}
