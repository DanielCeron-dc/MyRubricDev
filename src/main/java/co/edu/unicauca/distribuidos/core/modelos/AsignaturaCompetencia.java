package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "ASIGNATURA_COMPETENCIA", schema = "PUBLIC")
public class AsignaturaCompetencia {
    @EmbeddedId
    private AsignaturaCompetenciaId id;

    @MapsId("idAsignatura")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_ASIGNATURA", nullable = false)
    private Asignatura idAsignatura;

    @MapsId("idCompetencia")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_COMPETENCIA", nullable = false)
    private co.edu.unicauca.distribuidos.core.modelos.CompetenciasPrograma idCompetencia;

}