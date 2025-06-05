package co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;



public interface CompetenciaProgramaRepository extends JpaRepository<CompetenciaProgramaEntity, Integer> {
    List<CompetenciaProgramaEntity> findAllByOrderByCodigoAsc();

    CompetenciaProgramaEntity findByCodigo(@NotNull(message = "La competencia necesita un codigo") String codigo);
}