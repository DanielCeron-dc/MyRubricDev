package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RAActualizarRequestTO {
    
    @NotNull(message = "El ID del RA es obligatorio")
    private Integer id;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;
    
    @NotNull(message = "El ID de la competencia es obligatorio")
    private Integer idCompetencia;
    


}