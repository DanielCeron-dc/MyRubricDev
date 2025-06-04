package co.edu.unicauca.distribuidos.core.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "COMPETENCIAS_PROGRAMA", schema = "PUBLIC")
public class CompetenciasPrograma {
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

/*
 TODO [Reverse Engineering] create field to map the 'NIVEL' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "NIVEL", columnDefinition = "ENUM not null")
    private Object nivel;
*/
}