package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignacionAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.ResultadosAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.ResultadoAprendizajeDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RAActualizarRequestTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.CompetenciaAsignaturaRepository;

import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.RACrearRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.PeriodoAcademicoService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.RaAsignaturaService;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios.ResultadoProgramaRepository;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.DocenteRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultadoAprendizajeServiceImpl implements RaAsignaturaService {

    private final ResultadosAsignaturaRepository raRepository;
    private final ResultadoProgramaRepository resultadoProgramaRepository;
    private final CompetenciaAsignaturaRepository competenciaRepository;
    private final PeriodoAcademicoService periodoAcademicoService;
    private final AsignacionAsignaturaRepository asignacionRepository;
    private final DocenteRepositoryJPA docenteRepositoryJPA;

    @Transactional
    public ResultadoAprendizajeDTO crearRA(RACrearRequestDTO request) {
        validarPermisos(request.getCompetenciaId());

        CompetenciaAsignaturaEntity competencia = competenciaRepository.findById(request.getCompetenciaId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "La competencia no existe"));

        ResultadoAsignaturaEntity ra = new ResultadoAsignaturaEntity();
        ra.setDescripcion(request.getDescripcion());
        ra.setCompetencia(competencia);

        ResultadoAsignaturaEntity persisted = raRepository.save(ra);
        ResultadoAprendizajeDTO returnDto = new ResultadoAprendizajeDTO();
        returnDto.setId(persisted.getId());
        returnDto.setDescripcion(persisted.getDescripcion());
        returnDto.setCompetenciaAsignaturaId(persisted.getId());
        return returnDto;
    }

    @Override
    @Transactional
    public ResultadoAprendizajeDTO actualizarRA(RAActualizarRequestTO request) {
        validarPermisos(request.getCompetenciaId());

        ResultadoAsignaturaEntity ra = raRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Resultado de aprendizaje no existe"));

        ra.setDescripcion(request.getDescripcion());

        if (!ra.getCompetencia().getId().equals(request.getCompetenciaId())) {
            CompetenciaAsignaturaEntity nuevaCompetencia = competenciaRepository
                    .findById(request.getCompetenciaId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "La competencia no existe"));
            ra.setCompetencia(nuevaCompetencia);
        }


        ResultadoAsignaturaEntity persisted = raRepository.save(ra);
        ResultadoAprendizajeDTO returnDto = new ResultadoAprendizajeDTO();
        returnDto.setId(persisted.getId());
        returnDto.setDescripcion(persisted.getDescripcion());
        returnDto.setCompetenciaAsignaturaId(persisted.getId());
        return returnDto;
    }

    private void validarPermisos(Integer competenciaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean esDocente = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("DOCENTE"));

        if (esDocente) {
            String periodoActual = periodoAcademicoService.getPeriodoAcademico();
            Object principal = authentication.getPrincipal();
            if (!(principal instanceof UserDetails)) {
                throw new BusinessException(ErrorCode.UNAUTHORIZED, "No puedes modificar resultados de aprendizaje");
            }
            String username = authentication.getName();
            Integer docenteId = docenteRepositoryJPA.findByUsuarioUsername(username).get().getId();

            boolean tienePermiso = asignacionRepository
                    .existsByDocenteIdAndCompetenciaIdAndPeriodo(docenteId, competenciaId, periodoActual);

            if (!tienePermiso) {
                throw new BusinessException(ErrorCode.UNAUTHORIZED, "No tiene permisos para modificar resultados de aprendizaje en esta asignatura");
            }
        }
    }
}
