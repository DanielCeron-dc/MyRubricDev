package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.ResultadoAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignacionAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.ResultadosAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.ResultadoAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RAActualizarRequestTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.CompetenciaAsignaturaRepository;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.RACrearRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.PeriodoAcademicoService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.RaAsignaturaService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.CompetenciaAsignaturaMapper;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.ResultadoAsignaturaMapper;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoAprendizajeServiceImpl implements RaAsignaturaService {

    private final ResultadosAsignaturaRepository raRepository;
    private final CompetenciaAsignaturaRepository competenciaRepository;
    private final PeriodoAcademicoService periodoAcademicoService;
    private final AsignacionAsignaturaRepository asignacionRepository;
    private final DocenteRepositoryJPA docenteRepositoryJPA;

    private final ResultadoAsignaturaMapper resultadoAsignaturaMapper;
    private final CompetenciaAsignaturaMapper competenciaAsignaturaMapper;


    @Transactional
    public ResultadoAsignaturaDTO crearRA(RACrearRequestDTO request) {
        validarPermisos(request.getIdCompetencia());

        CompetenciaAsignaturaEntity competencia = competenciaRepository.findById(request.getIdCompetencia())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "La competencia no existe"));

        ResultadoAsignaturaEntity ra = resultadoAsignaturaMapper.toEntity(request, competencia);
        ResultadoAsignaturaEntity persisted = raRepository.save(ra);
        return resultadoAsignaturaMapper.toDTO(persisted, competenciaAsignaturaMapper.toDTO(persisted.getCompetencia()));
    }

    @Override
    @Transactional
    public ResultadoAsignaturaDTO actualizarRA(RAActualizarRequestTO request) {
        validarPermisos(request.getIdCompetencia());

        ResultadoAsignaturaEntity ra = raRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Resultado de aprendizaje no existe"));

        if (!ra.getCompetencia().getId().equals(request.getIdCompetencia())) {
            CompetenciaAsignaturaEntity nuevaCompetencia = competenciaRepository
                    .findById(request.getIdCompetencia())
                    .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "La competencia no existe"));
            ra.setCompetencia(nuevaCompetencia);
        }

        ResultadoAsignaturaEntity actResultado = resultadoAsignaturaMapper.toEntity(request, null);
        ResultadoAsignaturaEntity persisted = raRepository.save(actResultado);
        return resultadoAsignaturaMapper.toDTO(persisted, competenciaAsignaturaMapper.toDTO(persisted.getCompetencia()));
    }

    @Override
    public List<ResultadoAsignaturaDTO> listarResultadosAsignatura() {
        List<ResultadoAsignaturaEntity> resultados = raRepository.findAll();
        List<ResultadoAsignaturaDTO> resultadoDTOs = new LinkedList<>();
        for (ResultadoAsignaturaEntity resultado : resultados) {
            resultadoDTOs.add(resultadoAsignaturaMapper.toDTO(resultado, competenciaAsignaturaMapper.toDTO(resultado.getCompetencia())));
        }
        return resultadoDTOs;
    }

    @Override
    public List<ResultadoAsignaturaDTO> listarResultadosDeCompetencia(Integer idCompetencia) {
        List<ResultadoAsignaturaEntity> resultados = raRepository.findByCompetenciaId(idCompetencia);
        return resultados.stream().map((r) -> resultadoAsignaturaMapper.toDTO(r, competenciaAsignaturaMapper.toDTO(r.getCompetencia()))).toList();
    }

    /**
     * Valida si el docente tiene permisos para ejecutar la acción
     */
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
