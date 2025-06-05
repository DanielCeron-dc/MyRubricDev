package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RACrearRequestDTO {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;

    @NotNull(message = "El ID de la competencia es obligatorio")
    private Integer idCompetencia;

}