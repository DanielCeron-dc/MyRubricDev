package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignacionAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.CompetenciaAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignacionAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignacionService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.PeriodoAcademicoService;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.DocenteRepositoryJPA;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsignacionServiceImpl implements AsignacionService {

    private final AsignacionAsignaturaRepository asignacionRepository;
    private final UserRepository usuarioRepository;
    private final DocenteRepositoryJPA docenteRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final CompetenciaAsignaturaRepository competenciaRepository;
    // TODO: Fix, there is no academic period
    private final PeriodoAcademicoService periodoService;

    @Transactional
    @Override
    public AsignacionAsignaturaDTO asignarDocenteCompetencia(Integer docenteId, Integer asignaturaId, Integer competenciaId) {
        DocenteEntity docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Docente no encontrado"));

        AsignaturaEntity asignatura = asignaturaRepository.findById(asignaturaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Asignatura no encontrada"));

        CompetenciaAsignaturaEntity competencia = competenciaRepository.findById(competenciaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Competencia no encontrada"));

        String periodoActual = periodoService.getPeriodoAcademico();

        if (asignacionRepository.existsByDocenteIdAndAsignaturaIdAndPeriodo(
                docente.getId(),
                asignatura.getId(),
                periodoActual
        )) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "El docente ya está asignado a esta asignatura en el periodo actual");
        }

        AsignacionAsignaturaEntity asignacion = new AsignacionAsignaturaEntity();
        asignacion.setDocente(docente);
        asignacion.setAsignatura(asignatura);
        asignacion.setCompetencia(competencia);
        asignacion.setPeriodo(periodoActual);

        AsignacionAsignaturaEntity persisted = asignacionRepository.save(asignacion);
        AsignacionAsignaturaDTO returnDto = new AsignacionAsignaturaDTO();
        returnDto.setAsignaturaId(asignatura.getId());
        returnDto.setCompetenciaId(competencia.getId());
        returnDto.setDocenteId(docente.getId());
        return returnDto;
    }
}