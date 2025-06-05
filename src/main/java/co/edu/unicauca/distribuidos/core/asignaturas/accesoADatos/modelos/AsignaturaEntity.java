package co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "ASIGNATURAS")
public class AsignaturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "CODIGO", nullable = false, length = 20)
    private String codigo;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CREDITOS")
    private Integer creditos;

    @Lob
    @Column(name = "OBJETIVOS")
    private String objetivos;

    @Column(name = "SEMESTRE")
    private Integer semestre;

    @ColumnDefault("TRUE")
    @Column(name = "ACTIVA")
    private Boolean activa;

}