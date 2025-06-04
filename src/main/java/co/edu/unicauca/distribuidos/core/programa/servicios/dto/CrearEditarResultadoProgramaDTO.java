package co.edu.unicauca.distribuidos.core.programa.servicios.dto;


import lombok.Data;

@Data
public class CrearEditarResultadoProgramaDTO {
    private String codigo;
    private String descripcion;
    private Integer idCompetencia;
}
