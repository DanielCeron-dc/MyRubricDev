package co.edu.unicauca.distribuidos.core.modelos;

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
@Table(name = "NIVELES_DESEMPENO", schema = "PUBLIC")
public class NivelesDesempeno {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_CRITERIO", nullable = false)
    private Criterio idCriterio;

    @Size(max = 50)
    @NotNull
    @Column(name = "NOMBRE_NIVEL", nullable = false, length = 50)
    private String nombreNivel;

    @NotNull
    @Lob
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "NOTA_MIN", nullable = false, precision = 3, scale = 2)
    private BigDecimal notaMin;

    @NotNull
    @Column(name = "NOTA_MAX", nullable = false, precision = 3, scale = 2)
    private BigDecimal notaMax;

}