package co.edu.unicauca.distribuidos.core.asignaturas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RACrearRequest {
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "El ID de la competencia es obligatorio")
    private Long competenciaId;
    
    @NotNull(message = "El ID del resultado de programa es obligatorio")
    private Long resultadoProgramaId;

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCompetenciaId() {
        return competenciaId;
    }

    public void setCompetenciaId(Long competenciaId) {
        this.competenciaId = competenciaId;
    }

    public Long getResultadoProgramaId() {
        return resultadoProgramaId;
    }

    public void setResultadoProgramaId(Long resultadoProgramaId) {
        this.resultadoProgramaId = resultadoProgramaId;
    }
}