// AsignaturaEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturaEntity {
    private Integer id;
    private String nombre;
    private Integer creditos;
    private String objetivos;
    private Integer semestre;
    private List<CompetenciaAsignaturaEntity> competencias;
    private List<ResultadoAprendizajeAsignaturaEntity> resultados;
    private List<AsignacionCompDocenteEntity> asignacionesDocente;
}