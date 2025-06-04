package co.edu.unicauca.distribuidos.core.asignaturas.dto.request;

import jakarta.validation.constraints.NotNull;

public class CompetenciaAsignaturaRequest {
    
    @NotNull(message = "El ID de la competencia de programa es obligatorio")
    private Long competenciaProgramaId;
    
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Long asignaturaId;

    // Getters y Setters
    public Long getCompetenciaProgramaId() {
        return competenciaProgramaId;
    }

    public void setCompetenciaProgramaId(Long competenciaProgramaId) {
        this.competenciaProgramaId = competenciaProgramaId;
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }
}