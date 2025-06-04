package co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.modelos;

import co.edu.unicauca.distribuidos.core.rubrica.dominio.Criterio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "EVALUACION_CRITERIO", schema = "PUBLIC")
public class EvaluacionCriterioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_EVALUACION", nullable = false)
    private EvaluacionEntity idEvaluacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_CRITERIO", nullable = false)
    private Criterio idCriterio;

    @Size(max = 50)
    @Column(name = "NIVEL_SELECCIONADO", length = 50)
    private String nivelSeleccionado;

    @Column(name = "NOTA", precision = 4, scale = 2)
    private BigDecimal nota;

}