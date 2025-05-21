// EvaluacionEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionEntity {
    private Integer id;
    private Date fecha;
    private String comentarios;
    private RubricaEntity rubrica;
    private EstudianteEntity estudiante;
    private UsuarioEntity evaluador;
}