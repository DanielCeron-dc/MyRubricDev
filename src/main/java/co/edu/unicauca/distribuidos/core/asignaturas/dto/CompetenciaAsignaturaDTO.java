package co.edu.unicauca.distribuidos.core.asignaturas.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompetenciaAsignaturaDTO {
    private Integer id;
    @NotNull(message = "El ID de la competencia de programa es obligatorio")
    private Integer competenciaProgramaId;
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Integer asignaturaId;
}
