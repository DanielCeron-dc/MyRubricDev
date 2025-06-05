package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetenciaAsignaturaDTO {
    private Integer id;
    private String codigo;
    private String descripcion;

    private Integer competenciaProgramaId;
}
