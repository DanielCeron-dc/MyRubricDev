// RubricaEntity.java
package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAprendizajeAsignaturaEntity;

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