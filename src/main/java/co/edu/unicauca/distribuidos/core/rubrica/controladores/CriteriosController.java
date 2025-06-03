package co.edu.unicauca.distribuidos.core.rubrica.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.CriterioService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los criterios de evaluación
 */
@RestController
@RequestMapping("/rubrica")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Validated
public class CriteriosController {

    private final CriterioService criterioService;

    /**
     * Crear o actualizar un criterio de evaluación
     * Si el DTO incluye un ID, se trata como una actualización
     * Si no incluye ID, se trata como una creación
     * 
     * @param criterioDTO Datos del criterio a crear o actualizar
     * @return Criterio creado o actualizado
     */
    @PostMapping("/criterio")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<CriterioDTO> crearOActualizarCriterio(@RequestBody @Valid CriterioDTO criterioDTO) {
        if (criterioDTO.getId() == null) {
            // Crear nuevo criterio
            log.info("Creando nuevo criterio: {}", criterioDTO);
            CriterioDTO criterioCreado = criterioService.crearCriterio(criterioDTO);
            return new ResponseEntity<>(criterioCreado, HttpStatus.CREATED);
        } else {
            // Actualizar criterio existente
            log.info("Actualizando criterio con ID {}: {}", criterioDTO.getId(), criterioDTO);
            CriterioDTO criterioActualizado = criterioService.actualizarCriterio(criterioDTO);
            return ResponseEntity.ok(criterioActualizado);
        }
    }

    /**
     * Listar niveles de desempeño de un criterio específico
     * 
     * @param idRubrica ID de la rúbrica
     * @param idCriterio ID del criterio
     * @return Lista de niveles de desempeño
     */
    @GetMapping("/{id_rubrica}/criterio/{id_criterio}/niveles")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<List<NivelDesempenoDTO>> listarNivelesCriterio(
            @PathVariable("id_rubrica") @Valid @Min(value = 1, message = "El ID de la rúbrica debe ser mayor que 0") Integer idRubrica,
            @PathVariable("id_criterio") @Valid @Min(value = 1, message = "El ID del criterio debe ser mayor que 0") Integer idCriterio) {
        log.info("Solicitando niveles para el criterio {} de la rúbrica {}", idCriterio, idRubrica);
        List<NivelDesempenoDTO> niveles = criterioService.listarNivelesCriterio(idRubrica, idCriterio);
        return ResponseEntity.ok(niveles);
    }
}

