package co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ESTUDIANTES", schema = "PUBLIC")
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "NOMBRE_COMPLETO", length = 200)
    private String nombreCompleto;

    @Size(max = 100)
    @Column(name = "CORREO_INSTITUCIONAL", length = 100)
    private String correoInstitucional;

    @Column(name = "CODIGO", precision = 20)
    private BigDecimal codigo;

}