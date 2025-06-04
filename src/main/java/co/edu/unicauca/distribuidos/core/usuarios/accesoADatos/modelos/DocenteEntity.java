package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a teacher (docente) in the system
 */
@Entity
@Table(name = "DOCENTES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocenteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "APELLIDO", nullable = false)
    private String apellido;
    
    @Column(name = "CORREO_ACADEMICO", nullable = false, unique = true)
    private String correoAcademico;
    
    @Column(name = "TIPO_IDENTIFICACION", nullable = false)
    private String tipoIdentificacion;
    
    @Column(name = "IDENTIFICACION", nullable = false, unique = true)
    private String identificacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCENTE", nullable = false)
    private TipoDocente tipoDocente;
    
    @Column(name = "TITULO_ACADEMICO")
    private String tituloAcademico;
    
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", unique = true)
    private UsuarioEntity usuario;
}
