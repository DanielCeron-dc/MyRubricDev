// ResultadoAprendizajeProgramaEntity.java
package co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos;

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
@Table(name = "RESULTADOS_PROGRAMA", schema = "PUBLIC")
public class ResultadoAprendizajeProgramaEntity {
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
        @JoinColumn(name = "ID_COMPETENCIA")
        private CompetenciaProgramaEntity competenciaPrograma;

}