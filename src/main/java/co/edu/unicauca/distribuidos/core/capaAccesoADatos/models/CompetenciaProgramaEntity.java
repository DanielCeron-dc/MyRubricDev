// CompetenciaProgramaEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenciaProgramaEntity {
    private Integer id;
    private String descripcion;
    private List<ResultadoAprendizajeProgramaEntity> resultados;
}