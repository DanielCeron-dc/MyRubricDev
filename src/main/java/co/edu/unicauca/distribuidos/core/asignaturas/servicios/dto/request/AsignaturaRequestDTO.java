package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AsignaturaRequestDTO {

    private Integer id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotNull(message = "Los créditos son obligatorios")
    @Positive(message = "Los créditos deben ser un número positivo")
    private Integer creditos;
    
    private String objetivos;
    
    @NotNull(message = "El semestre es obligatorio")
    @Positive(message = "El semestre debe ser un número positivo")
    private Integer semestre;

    @NotNull(message = "El codigo de Asignatura es obligatorio")
    private String codigo;

}