package co.edu.unicauca.distribuidos.core.asignaturas.dto.request;

import jakarta.validation.constraints.NotNull;

public class AsignacionRequest {
    
    @NotNull(message = "El ID del docente es obligatorio")
    private Long docenteId;
    
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Long asignaturaId;
    
    @NotNull(message = "El ID de la competencia es obligatorio")
    private Long competenciaId;

    // Getters y Setters
    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Long getCompetenciaId() {
        return competenciaId;
    }

    public void setCompetenciaId(Long competenciaId) {
        this.competenciaId = competenciaId;
    }
}