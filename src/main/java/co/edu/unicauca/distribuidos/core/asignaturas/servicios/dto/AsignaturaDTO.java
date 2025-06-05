package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto;

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
    @NotNull(message = "El codigo es obligatorio")
    private String codigo;
    @NotNull(message = "Los objetivos son obligatorios")
    private String objetivos;
    @NotNull(message = "El semestre es obligatorio")
    private Integer semestre;
}
