package co.edu.unicauca.distribuidos.core.asignaturas.controladores;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignacionDocenteCompetenciaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.ResultadoAprendizajeDTO;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignaturaService;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.AsignacionRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.AsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.CompetenciaAsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RAActualizarRequestTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RACrearRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignacionService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.CompetenciaService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.RaAsignaturaService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/asignatura")
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService asignaturaService;
    private final AsignacionService asignacionService;
    private final CompetenciaService competenciaAsignaturaService;
    private final RaAsignaturaService resultadoAprendizajeService;


    /**
     * Lista todas las asignaturas del programa
     */
    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<AsignaturaDTO>> listarAsignaturas() {
        return ResponseEntity.ok(asignaturaService.listarAsignaturas());
    }

    /**
     * Crea una nueva asignatura
     * Permisos: Solo coordinador
     */
    @PostMapping
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignaturaDTO> crearAsignatura(
            @Valid @RequestBody AsignaturaRequestDTO request) {
        AsignaturaDTO nuevaAsignatura = asignaturaService.crearAsignatura(request);
        return new ResponseEntity<>(nuevaAsignatura, HttpStatus.CREATED);
    }

    /**
     * Actualiza una asignatura existente
     * Permisos: Solo coordinador
     */
    @PutMapping
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignaturaDTO> actualizarAsignatura(
            @Valid @RequestBody AsignaturaRequestDTO request) {
        AsignaturaDTO asignaturaActualizada = asignaturaService.actualizarAsignatura(request);
        return ResponseEntity.ok(asignaturaActualizada);
    }

    /**
     * Asigna un docente y una competencia a una asignatura
     * Permisos: Solo coordinador
     */
    @PostMapping("/asignar")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignacionDocenteCompetenciaDTO> asignarDocenteCompetencia(
            @Valid @RequestBody AsignacionRequestDTO request) {
        AsignacionDocenteCompetenciaDTO asignacion = asignacionService.asignarDocenteCompetencia(
                request.getDocenteId(),
                request.getAsignaturaId(),
                request.getCompetenciaId()
        );
        return new ResponseEntity<>(asignacion, HttpStatus.CREATED);
    }

    /**
     * Crea una nueva competencia para una asignatura
     * Permisos: Docente y coordinador
     */
    @PostMapping("/competencia")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<CompetenciaAsignaturaDTO> crearCompetenciaAsignatura(
            @Valid @RequestBody CompetenciaAsignaturaRequestDTO request) {
        CompetenciaAsignaturaDTO competencia = competenciaAsignaturaService.crearCompetencia(request);
        return new ResponseEntity<>(competencia, HttpStatus.CREATED);
    }

    /**
     * Actualiza una competencia de asignatura
     * Permisos: Docente y coordinador
     */
    @PutMapping("/competencia")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<CompetenciaAsignaturaDTO> actualizarCompetenciaAsignatura(
            @Valid @RequestBody CompetenciaAsignaturaRequestDTO request) {
        CompetenciaAsignaturaDTO competenciaActualizada = competenciaAsignaturaService.actualizarCompetencia(request);
        return ResponseEntity.ok(competenciaActualizada);
    }

    /**
     * Crea un nuevo resultado de aprendizaje (RA)
     * Permisos: Docente y coordinador
     */
    @PostMapping("/ra")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<ResultadoAprendizajeDTO> crearResultadoAprendizaje(
            @Valid @RequestBody RACrearRequestDTO request) {
        ResultadoAprendizajeDTO ra = resultadoAprendizajeService.crearRA(request);
        return new ResponseEntity<>(ra, HttpStatus.CREATED);
    }

    /**
     * Actualiza un resultado de aprendizaje (RA)
     * Permisos: Docente y coordinador
     */
    @PutMapping("/ra")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<ResultadoAprendizajeDTO> actualizarResultadoAprendizaje(
            @Valid @RequestBody RAActualizarRequestTO request) {
        ResultadoAprendizajeDTO raActualizado = resultadoAprendizajeService.actualizarRA(request);
        return ResponseEntity.ok(raActualizado);
    }
}