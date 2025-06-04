package co.edu.unicauca.distribuidos.core.rubrica.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.rubrica.servicios.NivelDesempenoService;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.NivelDesempenoDTO;
import co.edu.unicauca.distribuidos.core.rubrica.servicios.dto.CrearNivelDesempenoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador para gestionar las operaciones relacionadas con los niveles de desempeño
 */
@RestController
@RequestMapping("/api/rubrica/criterio")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Validated
public class NivelesController {

    private final NivelDesempenoService nivelDesempenoService;

    /**
     * Crear un nuevo nivel de desempeño
     * 
     * @param nivelDTO Datos del nivel de desempeño a crear
     * @return Nivel de desempeño creado
     */
    @PostMapping("/nivel")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<NivelDesempenoDTO> crearNivelDesempeno(@RequestBody @Valid CrearNivelDesempenoDTO nivelDTO) {
        log.info("Creando nuevo nivel de desempeño: {}", nivelDTO);
        NivelDesempenoDTO nivelCreado = nivelDesempenoService.crearNivelDesempeno(nivelDTO);
        return new ResponseEntity<>(nivelCreado, HttpStatus.CREATED);
    }

    /**
     * Actualizar un nivel de desempeño existente
     * 
     * @param nivelDTO Datos actualizados del nivel de desempeño
     * @return Nivel de desempeño actualizado
     */
    @PutMapping("/nivel")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<NivelDesempenoDTO> actualizarNivelDesempeno(@RequestBody @Valid NivelDesempenoDTO nivelDTO) {
        if (nivelDTO.getId() == null) {
            log.error("No se puede actualizar un nivel de desempeño sin ID");
            return ResponseEntity.badRequest().build();
        }
        
        log.info("Actualizando nivel de desempeño con ID {}: {}", nivelDTO.getId(), nivelDTO);
        NivelDesempenoDTO nivelActualizado = nivelDesempenoService.actualizarNivelDesempeno(nivelDTO);
        return ResponseEntity.ok(nivelActualizado);
    }
}

