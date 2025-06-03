package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "ASIGNATURA_RAP", schema = "PUBLIC")
public class AsignaturaRap {
    @EmbeddedId
    private AsignaturaRapId id;

    @MapsId("idAsignatura")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_ASIGNATURA", nullable = false)
    private Asignatura idAsignatura;

    @MapsId("idRap")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_RAP", nullable = false)
    private co.edu.unicauca.distribuidos.core.modelos.ResultadosPrograma idRap;

}