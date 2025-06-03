package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CRITERIOS", schema = "PUBLIC")
public class Criterio {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_RUBRICA", nullable = false)
    private co.edu.unicauca.distribuidos.core.modelos.Rubrica idRubrica;

    @NotNull
    @Lob
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "PONDERACION", nullable = false, precision = 5, scale = 2)
    private BigDecimal ponderacion;

}