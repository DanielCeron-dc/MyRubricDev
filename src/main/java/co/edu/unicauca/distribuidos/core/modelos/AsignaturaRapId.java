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
public class AsignaturaRapId implements java.io.Serializable {
    private static final long serialVersionUID = -8715514200331039377L;
    @NotNull
    @Column(name = "ID_ASIGNATURA", nullable = false)
    private Integer idAsignatura;

    @NotNull
    @Column(name = "ID_RAP", nullable = false)
    private Integer idRap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AsignaturaRapId entity = (AsignaturaRapId) o;
        return Objects.equals(this.idRap, entity.idRap) &&
                Objects.equals(this.idAsignatura, entity.idAsignatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRap, idAsignatura);
    }

}