package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ConfiguracionPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionPeriodoRepository extends JpaRepository<ConfiguracionPeriodo, Long> {
    Optional<ConfiguracionPeriodo> findByActivoTrue();
}
