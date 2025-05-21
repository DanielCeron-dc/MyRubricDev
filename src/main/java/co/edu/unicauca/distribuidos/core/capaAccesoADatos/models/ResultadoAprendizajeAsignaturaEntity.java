// ResultadoAprendizajeAsignaturaEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoAprendizajeAsignaturaEntity {
    private Integer id;
    private String descripcion;
    private CompetenciaAsignaturaEntity competenciaAsignatura;
    private ResultadoAprendizajeProgramaEntity resultadoPrograma;
}