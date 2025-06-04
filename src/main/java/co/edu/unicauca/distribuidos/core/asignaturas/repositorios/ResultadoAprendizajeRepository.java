package co.edu.unicauca.distribuidos.core.asignaturas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAprendizajeAsignaturaEntity;
@Repository
public interface ResultadoAprendizajeRepository extends JpaRepository<ResultadoAprendizajeAsignaturaEntity, Integer> {
    // Buscar resultados por competencia
    List<ResultadoAprendizajeAsignaturaEntity> findByCompetenciaAsignatura(CompetenciaAsignaturaEntity competencia);
    
    // Buscar resultados por programa
    List<ResultadoAprendizajeAsignaturaEntity> findByResultadoPrograma(ResultadoAprendizajeAsignaturaEntity programa);
} extends JpaRepository<ResultadoAprendizajeAsignaturaEntity, Integer> {
    // Buscar resultados por competencia
    List<ResultadoAprendizajeAsignaturaEntity> findByCompetenciaAsignatura(CompetenciaAsignaturaEntity competencia);
    
    // Buscar resultados por programa
    List<ResultadoAprendizajeAsignaturaEntity> findByResultadoPrograma(ResultadoAprendizajeProgramaEntity programa);
}