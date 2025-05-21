// EstudianteEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteEntity {
    private Integer id;
    private String nombre;
    private String correo;
    private Integer programaId;
    private List<EvaluacionEntity> evaluaciones;
}