package co.edu.unicauca.distribuidos.core.asignaturas.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResultadoAprendizajeDTO {
    private Integer id;
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    @NotNull(message = "El ID de la competencia es obligatorio")
    private Integer competenciaAsignaturaId;
    @NotNull(message = "El ID del resultado de programa es obligatorio")
    private Integer resultadoProgramaId;
}
