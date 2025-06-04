package co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface CompetenciaProgramaRepository extends JpaRepository<CompetenciaProgramaEntity, Integer> {
    List<CompetenciaProgramaEntity> findAllByOrderByCodigoAsc();
    //log.info("Se encontraron {} competencias", competencias.size());
}