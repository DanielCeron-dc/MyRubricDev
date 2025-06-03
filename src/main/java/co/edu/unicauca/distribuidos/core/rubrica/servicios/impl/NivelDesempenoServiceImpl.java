package co.edu.unicauca.distribuidos.core.rubrica.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.rubrica.dominio.Criterio;
import co.edu.unicauca.distribuidos.core.rubrica.dominio.NivelDesempeno;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.CriterioRepository;
import co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios.NivelDesempenoRepository;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.NivelDesempenoService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearNivelDesempenoDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del servicio que gestiona las operaciones relacionadas con los niveles de desempeño
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NivelDesempenoServiceImpl implements NivelDesempenoService {

    private final NivelDesempenoRepository nivelDesempenoRepository;
    private final CriterioRepository criterioRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public NivelDesempenoDTO crearNivelDesempeno(CrearNivelDesempenoDTO nivelDTO) {
        log.info("Creando nuevo nivel de desempeño: {}", nivelDTO);
        
        // Verificar que el criterio existe
        Criterio criterio = criterioRepository.findById(nivelDTO.getCriterioId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el criterio con ID: " + nivelDTO.getCriterioId()));
        
        // Validar el puntaje (debe ser positivo)
        if (nivelDTO.getPuntaje() <= 0) {
            throw new IllegalArgumentException("El puntaje del nivel de desempeño debe ser un valor positivo");
        }
        
        // Crear la entidad y establecer relaciones
        NivelDesempeno nivel = new NivelDesempeno();
        nivel.setDescripcion(nivelDTO.getDescripcion());
        nivel.setPuntaje(nivelDTO.getPuntaje());
        nivel.setCriterio(criterio);
        
        // Guardar el nivel de desempeño
        NivelDesempeno savedNivel = nivelDesempenoRepository.save(nivel);
        log.info("Nivel de desempeño creado con ID: {}", savedNivel.getId());
        
        return mapToDTO(savedNivel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NivelDesempenoDTO actualizarNivelDesempeno(NivelDesempenoDTO nivelDTO) {
        log.info("Actualizando nivel de desempeño con ID: {}", nivelDTO.getId());
        
        // Verificar que el nivel de desempeño existe
        NivelDesempeno nivel = nivelDesempenoRepository.findById(nivelDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el nivel de desempeño con ID: " + nivelDTO.getId()));
        
        // Verificar que el criterio existe si se cambió
        if (!nivel.getCriterio().getId().equals(nivelDTO.getCriterioId())) {
            Criterio nuevoCriterio = criterioRepository.findById(nivelDTO.getCriterioId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el criterio con ID: " + nivelDTO.getCriterioId()));
            nivel.setCriterio(nuevoCriterio);
        }
        
        // Validar el puntaje (debe ser positivo)
        if (nivelDTO.getPuntaje() <= 0) {
            throw new IllegalArgumentException("El puntaje del nivel de desempeño debe ser un valor positivo");
        }
        
        // Actualizar los campos
        nivel.setDescripcion(nivelDTO.getDescripcion());
        nivel.setPuntaje(nivelDTO.getPuntaje());
        
        // Guardar los cambios
        NivelDesempeno updatedNivel = nivelDesempenoRepository.save(nivel);
        log.info("Nivel de desempeño actualizado: {}", updatedNivel.getId());
        
        return mapToDTO(updatedNivel);
    }
    
    /**
     * Convierte una entidad NivelDesempeno a su DTO correspondiente
     * 
     * @param nivel Entidad a convertir
     * @return DTO del nivel de desempeño
     */
    private NivelDesempenoDTO mapToDTO(NivelDesempeno nivel) {
        return NivelDesempenoDTO.builder()
                .id(nivel.getId())
                .descripcion(nivel.getDescripcion())
                .puntaje(nivel.getPuntaje())
                .criterioId(nivel.getCriterio().getId())
                .build();
    }
    
    /**
     * Convierte un DTO de NivelDesempeno a su entidad correspondiente
     * Este método no establece la relación con el criterio
     * 
     * @param nivelDTO DTO a convertir
     * @return Entidad del nivel de desempeño
     */
    private NivelDesempeno mapToEntity(NivelDesempenoDTO nivelDTO) {
        NivelDesempeno nivel = new NivelDesempeno();
        nivel.setId(nivelDTO.getId());
        nivel.setDescripcion(nivelDTO.getDescripcion());
        nivel.setPuntaje(nivelDTO.getPuntaje());
        return nivel;
    }
}

