package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa una rúbrica de evaluación
 */
@Entity
@Table(name = "rubricas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rubrica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "El nombre de la rúbrica es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La descripción de la rúbrica es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(nullable = false, length = 20)
    private String estado;
    
    @Builder.Default
    @OneToMany(mappedBy = "rubrica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Criterio> criterios = new ArrayList<>();
    
    /**
     * Añade un criterio a la rúbrica estableciendo la relación bidireccional
     * 
     * @param criterio Criterio a añadir
     */
    public void addCriterio(Criterio criterio) {
        criterios.add(criterio);
        criterio.setRubrica(this);
    }
    
    /**
     * Elimina un criterio de la rúbrica rompiendo la relación bidireccional
     * 
     * @param criterio Criterio a eliminar
     */
    public void removeCriterio(Criterio criterio) {
        criterios.remove(criterio);
        criterio.setRubrica(null);
    }
}

