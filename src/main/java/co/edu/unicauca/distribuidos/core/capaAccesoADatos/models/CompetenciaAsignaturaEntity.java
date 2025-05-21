// CompetenciaAsignaturaEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenciaAsignaturaEntity {
    private Integer id;
    private CompetenciaProgramaEntity competenciaPrograma;
    private AsignaturaEntity asignatura;
    private List<ResultadoAprendizajeAsignaturaEntity> resultados;
}