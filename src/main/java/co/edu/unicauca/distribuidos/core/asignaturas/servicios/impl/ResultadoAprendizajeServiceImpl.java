package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionCompDocenteEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAprendizajeAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RAActualizarRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.CompetenciaAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.ResultadoAprendizajeRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.ResultadoAprendizajeService;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.ResultadoAprendizajeProgramaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultadoAprendizajeServiceImpl implements ResultadoAprendizajeService {

    private final ResultadoAprendizajeRepository raRepository;
    private final CompetenciaAsignaturaRepository competenciaRepository;
    private final PeriodoAcademicoService periodoService;

    @Override
    @Transactional
    public ResultadoAprendizajeAsignaturaEntity crearRA(RACreateRequest request) {
        validarPermisos(request.getCompetenciaId());
        
        CompetenciaAsignaturaEntity competencia = competenciaRepository.findById(request.getCompetenciaId())
                .orElseThrow(() -> new EntityNotFoundException("Competencia no encontrada"));
        
        ResultadoAprendizajeProgramaEntity resultadoPrograma = resultadoProgramaRepository
                .findById(request.getResultadoProgramaId())
                .orElseThrow(() -> new EntityNotFoundException("Resultado de programa no encontrado"));
        
        ResultadoAprendizajeAsignaturaEntity ra = new ResultadoAprendizajeAsignaturaEntity();
        ra.setDescripcion(request.getDescripcion());
        ra.setCompetenciaAsignatura(competencia);
        ra.setResultadoPrograma(resultadoPrograma);
        
        return raRepository.save(ra);
    }

    @Override
    @Transactional
    public ResultadoAprendizajeAsignaturaEntity actualizarRA(RAActualizarRequest request) {
        validarPermisos(request.getCompetenciaId());
        
        ResultadoAprendizajeAsignaturaEntity ra = raRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Resultado de aprendizaje no encontrado"));
        
        ra.setDescripcion(request.getDescripcion());
        
        if (!ra.getCompetenciaAsignatura().getId().equals(request.getCompetenciaId())) {
            CompetenciaAsignaturaEntity nuevaCompetencia = competenciaRepository
                    .findById(request.getCompetenciaId())
                    .orElseThrow(() -> new EntityNotFoundException("Competencia no encontrada"));
            ra.setCompetenciaAsignatura(nuevaCompetencia);
        }
        
        if (request.getResultadoProgramaId() != null && 
            (ra.getResultadoPrograma() == null || 
             !ra.getResultadoPrograma().getId().equals(request.getResultadoProgramaId()))) {
            ResultadoAprendizajeProgramaEntity nuevoResultado = resultadoProgramaRepository
                    .findById(request.getResultadoProgramaId())
                    .orElseThrow(() -> new EntityNotFoundException("Resultado de programa no encontrado"));
            ra.setResultadoPrograma(nuevoResultado);
        }
        
        return raRepository.save(ra);
    }

    private void validarPermisos(Long competenciaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("DOCENTE"))) {
            
            String periodoActual = periodoService.obtenerPeriodoActual();
            Long docenteId = ((UsuarioEntity) authentication.getPrincipal()).getId();
            
            if (!asignacionRepository.existsByUsuarioIdAndCompetenciaAsignaturaIdAndPeriodo(
                    docenteId, competenciaId, periodoActual)) {
                throw new AccessDeniedException(
                        "No tiene permisos para modificar resultados de aprendizaje en esta asignatura");
            }
        }
    }
}
