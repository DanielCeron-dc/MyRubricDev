// ResultadoAprendizajeProgramaEntity.java
package co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoAprendizajeProgramaEntity {
    private Integer id;
    private String descripcion;
    private CompetenciaProgramaEntity competenciaPrograma;
}