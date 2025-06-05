package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetenciaAsignaturaRequestDTO {

    private Integer id;


    @NotNull(message = "La competencia necesita una descripcion")
    private String descripcion;

    @NotNull(message = "La competencia necesita un codigo")
    private String codigo;

    @NotNull(message = "El ID de la competencia de programa es obligatorio")
    private Integer competenciaProgramaId;
}