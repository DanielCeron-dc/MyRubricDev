// RubricaEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RubricaEntity {
    private Integer id;
    private String descripcion;
    private BigDecimal ponderacion;
    private ResultadoAprendizajeAsignaturaEntity resultadoAsignatura;
    private List<CriterioEntity> criterios;
}