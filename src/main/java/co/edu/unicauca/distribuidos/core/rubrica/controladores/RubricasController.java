package co.edu.unicauca.distribuidos.core.rubrica.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.RubricaService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CriterioDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.RubricaDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearRubricaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Validated
public class RubricasController {

    private final RubricaService rubricaService;

    /**
     * Listar todas las rúbricas
     * @return Lista de rúbricas
     */
    @GetMapping("/rubricas")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<List<RubricaDTO>> listarRubricas() {
        log.info("Solicitando lista de rúbricas");
        // Llamada al servicio pendiente de implementar
        List<RubricaDTO> rubricas = rubricaService.listarRubricas();
        return ResponseEntity.ok(rubricas);
    }

    /**
     * Obtener una rúbrica por su ID
     * @param id ID de la rúbrica
     * @return Rúbrica encontrada
     */
    @GetMapping("/rubrica/{id}")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<RubricaDTO> obtenerRubricaPorId(
            @PathVariable @Valid @Min(value = 1, message = "El ID debe ser mayor que 0") Integer id) {
        log.info("Solicitando rúbrica con ID: {}", id);
        // Llamada al servicio pendiente de implementar
        RubricaDTO rubrica = rubricaService.obtenerRubricaPorId(id);
        return ResponseEntity.ok(rubrica);
    }

    /**
     * Crear una nueva rúbrica
     * @param rubricaDTO Datos de la rúbrica a crear
     * @return Rúbrica creada
     */
    @PostMapping("/rubrica")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<RubricaDTO> crearRubrica(@RequestBody @Valid CrearRubricaDTO rubricaDTO) {
        log.info("Creando nueva rúbrica: {}", rubricaDTO);
        // Llamada al servicio pendiente de implementar
        RubricaDTO rubricaCreada = rubricaService.crearRubrica(rubricaDTO);
        return new ResponseEntity<>(rubricaCreada, HttpStatus.CREATED);
    }

    /**
     * Actualizar una rúbrica existente
     * @param rubricaDTO Datos de la rúbrica a actualizar
     * @return Rúbrica actualizada
     */
    @PutMapping("/rubrica")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<RubricaDTO> actualizarRubrica(@RequestBody @Valid RubricaDTO rubricaDTO) {
        log.info("Actualizando rúbrica: {}", rubricaDTO);
        // Llamada al servicio pendiente de implementar
        RubricaDTO rubricaActualizada = rubricaService.actualizarRubrica(rubricaDTO);
        return ResponseEntity.ok(rubricaActualizada);
    }

    /**
     * Listar criterios de evaluación de una rúbrica
     * @param id ID de la rúbrica
     * @return Lista de criterios de evaluación
     */
    @GetMapping("/rubrica/{id}/criterios")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<List<CriterioDTO>> listarCriteriosRubrica(
            @PathVariable @Valid @Min(value = 1, message = "El ID debe ser mayor que 0") Integer id) {
        log.info("Solicitando criterios para la rúbrica con ID: {}", id);
        // Llamada al servicio pendiente de implementar
        List<CriterioDTO> criterios = rubricaService.listarCriteriosRubrica(id);
        return ResponseEntity.ok(criterios);
    }
}
