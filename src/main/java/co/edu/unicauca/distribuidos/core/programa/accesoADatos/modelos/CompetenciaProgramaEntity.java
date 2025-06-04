// CompetenciaProgramaEntity.java
package co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "COMPETENCIAS_PROGRAMA", schema = "PUBLIC")
public class CompetenciaProgramaEntity{
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

    @Column(name = "NIVEL", columnDefinition = "ENUM not null")
    private NivelCompetencia nivel;
}