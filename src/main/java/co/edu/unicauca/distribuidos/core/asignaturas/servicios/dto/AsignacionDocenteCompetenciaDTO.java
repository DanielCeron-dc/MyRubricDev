package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignacionDocenteCompetenciaDTO {
    @NotNull(message = "El ID del docente es obligatorio")
    private Integer docenteId;
    
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Integer asignaturaId;
    
    @NotNull(message = "El ID de la competencia es obligatorio")
    private Integer competenciaId;
}
