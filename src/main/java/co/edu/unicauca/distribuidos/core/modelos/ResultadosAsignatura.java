package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "RESULTADOS_ASIGNATURA", schema = "PUBLIC")
public class ResultadosAsignatura {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_ASIGNATURA", nullable = false)
    private Asignatura idAsignatura;

}