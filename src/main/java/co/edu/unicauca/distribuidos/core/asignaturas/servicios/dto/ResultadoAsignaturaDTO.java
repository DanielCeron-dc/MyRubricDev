package co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto;

import lombok.Data;

@Data
public class ResultadoAsignaturaDTO {
    private Integer id;

    private String descripcion;

    private String codigo;

    private CompetenciaAsignaturaDTO competenciaAsignatura;
}
