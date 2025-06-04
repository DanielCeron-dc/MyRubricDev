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
@Table(name = "CONFIGURACION_PERIODO", schema = "PUBLIC")
public class ConfiguracionPeriodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "PERIODO", nullable = false, length = 50)
    private String periodo;

    @NotNull
    @ColumnDefault("TRUE")
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = false;

}