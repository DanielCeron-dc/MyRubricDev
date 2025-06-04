package co.edu.unicauca.distribuidos.core.asignaturas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignaturaService;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionCompDocenteEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAprendizajeAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.AsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.ResultadoAprendizajeDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignacionRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignaturaRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.CompetenciaAsignaturaRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RAActualizarRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RACrearRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignacionService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.CompetenciaService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.ResultadoAprendizajeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asignatura")
@RequiredArgsConstructor
public class AsignaturaController {
    
    private final AsignaturaService asignaturaService;
    private final AsignacionService asignacionService;
    private final CompetenciaService competenciaAsignaturaService;
    private final ResultadoAprendizajeService resultadoAprendizajeService;

    /**
     * Crea una nueva asignatura
     * Permisos: Solo coordinador
     */
    @PostMapping
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignaturaEntity> crearAsignatura(
            @Valid @RequestBody AsignaturaRequest request) {
        AsignaturaEntity nuevaAsignatura = asignaturaService.crearAsignatura(request);
        return new ResponseEntity<>(nuevaAsignatura, HttpStatus.CREATED);
    }

    /**
     * Actualiza una asignatura existente
     * Permisos: Solo coordinador
     */
    @PutMapping
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignaturaEntity> actualizarAsignatura(
            @Valid @RequestBody AsignaturaRequest request) {
        AsignaturaEntity asignaturaActualizada = asignaturaService.actualizarAsignatura(request);
        return ResponseEntity.ok(asignaturaActualizada);
    }

    /**
     * Asigna un docente y una competencia a una asignatura
     * Permisos: Solo coordinador
     */
    @PostMapping("/asignar")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<AsignacionCompDocenteEntity> asignarDocenteCompetencia(
            @Valid @RequestBody AsignacionRequest request) {
        AsignacionCompDocenteEntity asignacion = asignacionService.asignarDocenteCompetencia(
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
    public ResponseEntity<CompetenciaAsignaturaEntity> crearCompetenciaAsignatura(
            @Valid @RequestBody CompetenciaAsignaturaRequest request) {
        CompetenciaAsignaturaEntity competencia = competenciaAsignaturaService.crearCompetencia(request);
        return new ResponseEntity<>(competencia, HttpStatus.CREATED);
    }

    /**
     * Actualiza una competencia de asignatura
     * Permisos: Docente y coordinador
     */
    @PutMapping("/competencia")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<CompetenciaAsignaturaEntity> actualizarCompetenciaAsignatura(
            @Valid @RequestBody CompetenciaAsignaturaRequest request) {
        CompetenciaAsignaturaEntity competenciaActualizada = competenciaAsignaturaService.actualizarCompetencia(request);
        return ResponseEntity.ok(competenciaActualizada);
    }

    /**
     * Crea un nuevo resultado de aprendizaje (RA)
     * Permisos: Docente y coordinador
     */
    @PostMapping("/ra")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<ResultadoAprendizajeAsignaturaEntity> crearResultadoAprendizaje(
            @Valid @RequestBody RACrearRequest request) {
        ResultadoAprendizajeAsignaturaEntity ra = resultadoAprendizajeService.crearRA(request);
        return new ResponseEntity<>(ra, HttpStatus.CREATED);
    }

    /**
     * Actualiza un resultado de aprendizaje (RA)
     * Permisos: Docente y coordinador
     */
    @PutMapping("/ra")
    @PreAuthorize("hasAnyRole('DOCENTE', 'COORDINADOR')")
    public ResponseEntity<ResultadoAprendizajeAsignaturaEntity> actualizarResultadoAprendizaje(
            @Valid @RequestBody RAActualizarRequest request) {
        ResultadoAprendizajeAsignaturaEntity raActualizado = resultadoAprendizajeService.actualizarRA(request);
        return ResponseEntity.ok(raActualizado);
    }
}