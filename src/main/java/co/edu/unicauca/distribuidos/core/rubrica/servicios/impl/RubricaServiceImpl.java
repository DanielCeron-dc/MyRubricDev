package co.edu.unicauca.distribuidos.core.rubrica.servicios.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.modelos.Rubrica;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.CriterioRepository;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.RubricaRepository;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.RubricaService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearRubricaDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.RubricaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del servicio que gestiona las operaciones relacionadas con las rúbricas
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RubricaServiceImpl implements RubricaService {

    private final RubricaRepository rubricaRepository;
    private final CriterioRepository criterioRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<RubricaDTO> listarRubricas() {
        log.info("Obteniendo lista de todas las rúbricas");
        return rubricaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public RubricaDTO obtenerRubricaPorId(Integer id) {
        log.info("Buscando rúbrica con ID: {}", id);
        return rubricaRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la rúbrica con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RubricaDTO crearRubrica(CrearRubricaDTO rubricaDTO) {
        log.info("Creando nueva rúbrica: {}", rubricaDTO);
        
        // Verificar si ya existe una rúbrica con el mismo nombre
        if (rubricaRepository.existsByNombreIgnoreCase(rubricaDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una rúbrica con el nombre: " + rubricaDTO.getNombre());
        }
        
        // Crear y guardar la nueva entidad
        Rubrica rubrica = new Rubrica();
        rubrica.setNombre(rubricaDTO.getNombre());
        rubrica.setDescripcion(rubricaDTO.getDescripcion());
        rubrica.setFechaCreacion(LocalDateTime.now());
        rubrica.setEstado("ACTIVO");
        
        Rubrica savedRubrica = rubricaRepository.save(rubrica);
        log.info("Rúbrica creada con ID: {}", savedRubrica.getId());
        
        return mapToDTO(savedRubrica);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RubricaDTO actualizarRubrica(RubricaDTO rubricaDTO) {
        log.info("Actualizando rúbrica con ID: {}", rubricaDTO.getId());
        
        // Verificar que la rúbrica existe
        Rubrica rubrica = rubricaRepository.findById(rubricaDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la rúbrica con ID: " + rubricaDTO.getId()));
        
        // Verificar si el nuevo nombre ya está en uso por otra rúbrica
        if (!rubrica.getNombre().equalsIgnoreCase(rubricaDTO.getNombre()) && 
                rubricaRepository.existsByNombreIgnoreCase(rubricaDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe otra rúbrica con el nombre: " + rubricaDTO.getNombre());
        }
        
        // Actualizar los campos
        rubrica.setNombre(rubricaDTO.getNombre());
        rubrica.setDescripcion(rubricaDTO.getDescripcion());
        if (rubricaDTO.getEstado() != null) {
            rubrica.setEstado(rubricaDTO.getEstado());
        }
        
        Rubrica updatedRubrica = rubricaRepository.save(rubrica);
        log.info("Rúbrica actualizada: {}", updatedRubrica.getId());
        
        return mapToDTO(updatedRubrica);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CriterioDTO> listarCriteriosRubrica(Integer id) {
        log.info("Obteniendo criterios para la rúbrica con ID: {}", id);
        
        // Verificar que la rúbrica existe
        if (!rubricaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró la rúbrica con ID: " + id);
        }
        
        return criterioRepository.findByRubricaId(id).stream()
                .map(criterio -> CriterioDTO.builder()
                        .id(criterio.getId())
                        .nombre(criterio.getNombre())
                        .descripcion(criterio.getDescripcion())
                        .peso(criterio.getPeso())
                        .rubricaId(criterio.getRubrica().getId())
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * Convierte una entidad Rubrica a su DTO correspondiente
     * 
     * @param rubrica Entidad a convertir
     * @return DTO de la rúbrica
     */
    private RubricaDTO mapToDTO(Rubrica rubrica) {
        return RubricaDTO.builder()
                .id(rubrica.getId())
                .nombre(rubrica.getNombre())
                .descripcion(rubrica.getDescripcion())
                .fechaCreacion(rubrica.getFechaCreacion())
                .estado(rubrica.getEstado())
                .build();
    }
    
    /**
     * Convierte un DTO de Rubrica a su entidad correspondiente
     * 
     * @param rubricaDTO DTO a convertir
     * @return Entidad de la rúbrica
     */
    private Rubrica mapToEntity(RubricaDTO rubricaDTO) {
        Rubrica rubrica = new Rubrica();
        rubrica.setId(rubricaDTO.getId());
        rubrica.setNombre(rubricaDTO.getNombre());
        rubrica.setDescripcion(rubricaDTO.getDescripcion());
        rubrica.setEstado(rubricaDTO.getEstado());
        
        // La fecha de creación no se actualiza desde el DTO
        if (rubricaDTO.getFechaCreacion() != null) {
            rubrica.setFechaCreacion(rubricaDTO.getFechaCreacion());
        } else {
            rubrica.setFechaCreacion(LocalDateTime.now());
        }
        
        return rubrica;
    }
}

