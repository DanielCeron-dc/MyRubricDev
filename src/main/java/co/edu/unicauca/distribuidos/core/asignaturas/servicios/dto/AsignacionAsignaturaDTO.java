package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignacionAsignaturaDTO {
    private Integer id;
    @NotNull(message = "El ID del docente es obligatorio")
    private DocenteDTO docente;
    
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private AsignaturaDTO asignatura;
    
    @NotNull(message = "El ID de la competencia es obligatorio")
    private CompetenciaAsignaturaDTO competencia;

    private String periodo;
}
