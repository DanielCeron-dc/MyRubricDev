// UsuarioEntity.java
package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    private Integer id;
    private String username;
    private String passwordHash;
    private Rol rol;
    private List<AsignacionCompDocenteEntity> asignacionesDocente;
    private List<EvaluacionEntity> evaluaciones;
}