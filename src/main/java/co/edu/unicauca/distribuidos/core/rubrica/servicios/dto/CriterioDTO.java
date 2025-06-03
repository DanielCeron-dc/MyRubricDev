package co.edu.unicauca.distribuidos.core.rubrica.servicios.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir información de criterios de evaluación
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioDTO {
    
    private Integer id;
    
    @NotBlank(message = "El nombre del criterio es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La descripción del criterio es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El peso del criterio es obligatorio")
    @Positive(message = "El peso debe ser un valor positivo")
    private Double peso;
    
    @NotNull(message = "El ID de la rúbrica es obligatorio")
    @Min(value = 1, message = "El ID de la rúbrica debe ser mayor que 0")
    private Integer rubricaId;
}

