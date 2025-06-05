package co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
 * Entidad que representa un criterio de evaluación dentro de una rúbrica
 */
@Entity
@Table(name = "criterios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Criterio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "El nombre del criterio es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La descripción del criterio es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @NotNull(message = "El peso del criterio es obligatorio")
    @Positive(message = "El peso debe ser un valor positivo")
    @Column(nullable = false)
    private Double peso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubrica_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Rubrica rubrica;
    
    @Builder.Default
    @OneToMany(mappedBy = "criterio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NivelDesempeno> nivelesDesempeno = new ArrayList<>();
    
    /**
     * Añade un nivel de desempeño al criterio estableciendo la relación bidireccional
     * 
     * @param nivel Nivel de desempeño a añadir
     */
    public void addNivelDesempeno(NivelDesempeno nivel) {
        nivelesDesempeno.add(nivel);
        nivel.setCriterio(this);
    }
    
    /**
     * Elimina un nivel de desempeño del criterio rompiendo la relación bidireccional
     * 
     * @param nivel Nivel de desempeño a eliminar
     */
    public void removeNivelDesempeno(NivelDesempeno nivel) {
        nivelesDesempeno.remove(nivel);
        nivel.setCriterio(null);
    }
}

