package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DocenteEntity {
    private int Id;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    private Date fechaRetiro;
}