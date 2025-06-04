package co.edu.unicauca.distribuidos.core.asignaturas.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignaturaDTO {
    private Integer id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "Los créditos son obligatorios")
    private Integer creditos;
    private String objetivos;
    @NotNull(message = "El semestre es obligatorio")
    private Integer semestre;
}
