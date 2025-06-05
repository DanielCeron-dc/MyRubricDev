package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa un nivel de desempeño dentro de un criterio de evaluación
 */
@Entity
@Table(name = "niveles_desempeno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelDesempeno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "La descripción del nivel de desempeño es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @NotNull(message = "El puntaje del nivel de desempeño es obligatorio")
    @Positive(message = "El puntaje debe ser un valor positivo")
    @Column(nullable = false)
    private Double puntaje;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Criterio criterio;
}

