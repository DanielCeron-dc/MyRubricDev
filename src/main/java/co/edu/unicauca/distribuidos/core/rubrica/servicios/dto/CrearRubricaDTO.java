package co.edu.unicauca.distribuidos.core.rubrica.servicios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de rúbricas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearRubricaDTO {
    
    @NotBlank(message = "El nombre de la rúbrica es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La descripción de la rúbrica es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}

