package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos;

import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "COMPETENCIAS_ASIGNATURA")
public class CompetenciaAsignaturaEntity {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "CODIGO", nullable = false, length = 20)
    private String codigo;

    @NotNull
    @Lob
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @NotNull
    @JoinColumn(name = "ID_ASIGNATURA")
    private AsignaturaEntity asignatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @NotNull
    @JoinColumn(name = "ID_COMPETENCIA")
    private CompetenciaProgramaEntity competenciaPrograma;

}