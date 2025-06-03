package co.edu.unicauca.distribuidos.core.modelos;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "EVALUACIONES", schema = "PUBLIC")
public class Evaluacion {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_ESTUDIANTE", nullable = false)
    private DocenteEntity idEstudiante;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_RUBRICA", nullable = false)
    private co.edu.unicauca.distribuidos.core.modelos.Rubrica idRubrica;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_EVALUADOR", nullable = false)
    private DocenteEntity idEvaluador;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "FECHA")
    private LocalDate fecha;

    @Lob
    @Column(name = "COMENTARIOS")
    private String comentarios;

    @ColumnDefault("FALSE")
    @Column(name = "FINALIZADA")
    private Boolean finalizada;

}