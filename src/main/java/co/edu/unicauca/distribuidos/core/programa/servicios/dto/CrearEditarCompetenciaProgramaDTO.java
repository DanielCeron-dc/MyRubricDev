package co.edu.unicauca.distribuidos.core.programa.servicios.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearEditarCompetenciaProgramaDTO {
    @NotNull(message = "El codigo es requerido")
    private String codigo;

    @NotNull(message = "La descripcion es requerida")
    private String descripcion;

    @NotNull(message = "El nivel es requerido")
    private String nivel;
}
