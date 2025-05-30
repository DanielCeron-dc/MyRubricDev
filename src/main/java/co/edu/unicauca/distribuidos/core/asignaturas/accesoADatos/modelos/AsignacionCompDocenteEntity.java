// AsignacionCompDocenteEntity.java
package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionCompDocenteEntity {
    private AsignaturaEntity asignatura;
    private UsuarioEntity usuario;
    private CompetenciaAsignaturaEntity competenciaAsignatura;
    private String periodo;
}