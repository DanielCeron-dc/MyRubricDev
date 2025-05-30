// EvaluacionEntity.java
package co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos.RubricaEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.UsuarioEntity;

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