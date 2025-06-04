package co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.ResultadoAprendizajeProgramaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoProgramaRepository extends JpaRepository<ResultadoAprendizajeProgramaEntity, Integer> {
    List<ResultadoAprendizajeProgramaEntity> findByCompetenciaPrograma_Id(Integer idCompetencia);
}