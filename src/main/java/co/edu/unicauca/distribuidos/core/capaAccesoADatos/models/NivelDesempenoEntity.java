// NivelDesempenoEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NivelDesempenoEntity {
    private Integer id;
    private String descripcion;
    private String rangoNota;
    private CriterioEntity criterio;
}