// CriterioEntity.java
package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriterioEntity {
    private Integer id;
    private String descripcion;
    private BigDecimal ponderacion;
    private RubricaEntity rubrica;
    private List<NivelDesempenoEntity> niveles;
}