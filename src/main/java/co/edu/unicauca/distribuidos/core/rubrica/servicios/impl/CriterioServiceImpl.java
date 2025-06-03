package co.edu.unicauca.distribuidos.core.rubrica.servicios.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.rubrica.dominio.Criterio;
import co.edu.unicauca.distribuidos.core.rubrica.dominio.Rubrica;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.CriterioRepository;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.NivelDesempenoRepository;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.RubricaRepository;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.CriterioService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del servicio que gestiona las operaciones relacionadas con los criterios de evaluación
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CriterioServiceImpl implements CriterioService {

    private final CriterioRepository criterioRepository;
    private final RubricaRepository rubricaRepository;
    private final NivelDesempenoRepository nivelDesempenoRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CriterioDTO crearCriterio(CriterioDTO criterioDTO) {
        log.info("Creando nuevo criterio: {}", criterioDTO);
        
        // Verificar que la rúbrica existe
        Rubrica rubrica = rubricaRepository.findById(criterioDTO.getRubricaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la rúbrica con ID: " + criterioDTO.getRubricaId()));
        
        // Validar el peso del criterio (debe ser positivo)
        if (criterioDTO.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso del criterio debe ser un valor positivo");
        }
        
        // Crear la entidad y establecer relaciones
        Criterio criterio = new Criterio();
        criterio.setNombre(criterioDTO.getNombre());
        criterio.setDescripcion(criterioDTO.getDescripcion());
        criterio.setPeso(criterioDTO.getPeso());
        criterio.setRubrica(rubrica);
        
        // Guardar el criterio
        Criterio savedCriterio = criterioRepository.save(criterio);
        log.info("Criterio creado con ID: {}", savedCriterio.getId());
        
        return mapToDTO(savedCriterio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CriterioDTO actualizarCriterio(CriterioDTO criterioDTO) {
        log.info("Actualizando criterio con ID: {}", criterioDTO.getId());
        
        // Verificar que el criterio existe
        Criterio criterio = criterioRepository.findById(criterioDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el criterio con ID: " + criterioDTO.getId()));
        
        // Verificar que la rúbrica existe si se cambió
        if (!criterio.getRubrica().getId().equals(criterioDTO.getRubricaId())) {
            Rubrica nuevaRubrica = rubricaRepository.findById(criterioDTO.getRubricaId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la rúbrica con ID: " + criterioDTO.getRubricaId()));
            criterio.setRubrica(nuevaRubrica);
        }
        
        // Validar el peso del criterio (debe ser positivo)
        if (criterioDTO.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso del criterio debe ser un valor positivo");
        }
        
        // Actualizar los campos
        criterio.setNombre(criterioDTO.getNombre());
        criterio.setDescripcion(criterioDTO.getDescripcion());
        criterio.setPeso(criterioDTO.getPeso());
        
        // Guardar los cambios
        Criterio updatedCriterio = criterioRepository.save(criterio);
        log.info("Criterio actualizado: {}", updatedCriterio.getId());
        
        return mapToDTO(updatedCriterio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<NivelDesempenoDTO> listarNivelesCriterio(Integer idRubrica, Integer idCriterio) {
        log.info("Obteniendo niveles para el criterio {} de la rúbrica {}", idCriterio, idRubrica);
        
        // Verificar que la rúbrica existe
        if (!rubricaRepository.existsById(idRubrica)) {
            throw new EntityNotFoundException("No se encontró la rúbrica con ID: " + idRubrica);
        }
        
        // Verificar que el criterio existe y pertenece a la rúbrica
        criterioRepository.findByRubricaIdAndId(idRubrica, idCriterio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el criterio con ID: " + idCriterio + " en la rúbrica con ID: " + idRubrica));
        
        // Obtener los niveles de desempeño
        return nivelDesempenoRepository.findByCriterioRubricaIdAndCriterioId(idRubrica, idCriterio)
                .stream()
                .map(nivel -> NivelDesempenoDTO.builder()
                        .id(nivel.getId())
                        .descripcion(nivel.getDescripcion())
                        .puntaje(nivel.getPuntaje())
                        .criterioId(nivel.getCriterio().getId())
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * Convierte una entidad Criterio a su DTO correspondiente
     * 
     * @param criterio Entidad a convertir
     * @return DTO del criterio
     */
    private CriterioDTO mapToDTO(Criterio criterio) {
        return CriterioDTO.builder()
                .id(criterio.getId())
                .nombre(criterio.getNombre())
                .descripcion(criterio.getDescripcion())
                .peso(criterio.getPeso())
                .rubricaId(criterio.getRubrica().getId())
                .build();
    }
    
    /**
     * Convierte un DTO de Criterio a su entidad correspondiente
     * Este método no establece la relación con la rúbrica
     * 
     * @param criterioDTO DTO a convertir
     * @return Entidad del criterio
     */
    private Criterio mapToEntity(CriterioDTO criterioDTO) {
        Criterio criterio = new Criterio();
        criterio.setId(criterioDTO.getId());
        criterio.setNombre(criterioDTO.getNombre());
        criterio.setDescripcion(criterioDTO.getDescripcion());
        criterio.setPeso(criterioDTO.getPeso());
        return criterio;
    }
}

