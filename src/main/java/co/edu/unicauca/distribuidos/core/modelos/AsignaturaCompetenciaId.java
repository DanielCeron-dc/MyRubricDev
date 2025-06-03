package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AsignaturaCompetenciaId implements java.io.Serializable {
    private static final long serialVersionUID = -2359621557961091830L;
    @NotNull
    @Column(name = "ID_ASIGNATURA", nullable = false)
    private Integer idAsignatura;

    @NotNull
    @Column(name = "ID_COMPETENCIA", nullable = false)
    private Integer idCompetencia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AsignaturaCompetenciaId entity = (AsignaturaCompetenciaId) o;
        return Objects.equals(this.idAsignatura, entity.idAsignatura) &&
                Objects.equals(this.idCompetencia, entity.idCompetencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAsignatura, idCompetencia);
    }

}