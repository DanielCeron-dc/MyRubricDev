package co.edu.unicauca.distribuidos.core.usuarios.servicios.dto;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.TipoDocente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearDocenteDTO {

    private Integer id;    
    
    private String nombre;    
    
    private String apellido;    
    
    private String correoAcademico;
    
    private String tipoIdentificacion;
    
    private String identificacion;
    
    private TipoDocente tipoDocente;   
    
    private String tituloAcademico;    
    
    private Boolean activo;
    
}
