// CompetenciaAsignaturaEntity.java
package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenciaAsignaturaEntity {
    private Integer id;
    private CompetenciaProgramaEntity competenciaPrograma;
    private AsignaturaEntity asignatura;
    private List<ResultadoAprendizajeAsignaturaEntity> resultados;
}