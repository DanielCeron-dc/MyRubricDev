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
 * DTO para transferir información de niveles de desempeño
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelDesempenoDTO {
    
    private Integer id;
    
    @NotBlank(message = "La descripción del nivel de desempeño es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El puntaje del nivel de desempeño es obligatorio")
    @Positive(message = "El puntaje debe ser un valor positivo")
    private Double puntaje;
    
    @NotNull(message = "El ID del criterio es obligatorio")
    @Min(value = 1, message = "El ID del criterio debe ser mayor que 0")
    private Integer criterioId;
}

