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
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.AsignaturaMapper;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.CompetenciaAsignaturaMapper;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios.CompetenciaProgramaRepository;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.DocenteRepositoryJPA;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.UserRepository;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper.DocenteMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignacionServiceImpl implements AsignacionService {

    private final AsignacionAsignaturaRepository asignacionRepository;
    private final DocenteRepositoryJPA docenteRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final CompetenciaAsignaturaRepository competenciaAsignaturaRepository;
    private final CompetenciaProgramaRepository competenciaProgramaRepository;
    private final UserRepository userRepository;
    private final PeriodoAcademicoService periodoService;
    private final CompetenciaAsignaturaMapper competenciaAsignaturaMapper;
    private final AsignaturaMapper asignaturaMapper;
    private final DocenteMapper getDocenteMapper;

    @Transactional
    @Override
    public AsignacionAsignaturaDTO asignarDocenteCompetencia(Integer docenteId, Integer asignaturaId, Integer competenciaId) {
        DocenteEntity docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Docente no encontrado"));

        AsignaturaEntity asignatura = asignaturaRepository.findById(asignaturaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Asignatura no encontrada"));

        CompetenciaProgramaEntity competenciaPrograma = competenciaProgramaRepository.findById(competenciaId).orElseThrow(() ->
                new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "No se encontro una competencia con el id" + competenciaId));

        // Creamos una copia de esta competencia
        CompetenciaAsignaturaEntity competenciaAsignatura = new CompetenciaAsignaturaEntity();
        competenciaAsignatura.setCodigo(competenciaPrograma.getCodigo() + asignaturaId.toString() + docenteId.toString());
        competenciaAsignatura.setDescripcion(competenciaPrograma.getDescripcion());
        competenciaAsignatura.setCompetenciaPrograma(competenciaPrograma);

        competenciaAsignatura = competenciaAsignaturaRepository.save(competenciaAsignatura);

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
        asignacion.setCompetencia(competenciaAsignatura);
        asignacion.setPeriodo(periodoActual);

        asignacion = asignacionRepository.save(asignacion);

        return mapearAsignacionADTO(asignacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionAsignaturaDTO> listarAsignaciones() {
        List<AsignacionAsignaturaEntity> asignaciones = asignacionRepository.findAll();

        return asignaciones.stream()
                .map(this::mapearAsignacionADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionAsignaturaDTO> listarAsignacionesAsociadas() {
        // Obtener el usuario autenticado
        String username = obtenerUsernameAutenticado();

        // Buscar el usuario por username
        var usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Usuario no encontrado: " + username));

        // Buscar el docente asociado al usuario
        DocenteEntity docente = docenteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "No se encontró un docente asociado al usuario: " + username));

        // Obtener las asignaciones del docente
        List<AsignacionAsignaturaEntity> asignaciones = asignacionRepository.findByDocenteId(docente.getId());

        return asignaciones.stream()
                .map(this::mapearAsignacionADTO)
                .collect(Collectors.toList());
    }

    @Override
    public AsignacionAsignaturaDTO buscarAsignacionPorId(Integer id) {
        return mapearAsignacionADTO(asignacionRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "No se encontro una asociacion con el id: " + id)));
    }

    /**
     * Método auxiliar para obtener el username del usuario autenticado
     */
    private String obtenerUsernameAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        throw new BusinessException(ErrorCode.UNAUTHORIZED, "No se pudo obtener el usuario autenticado");
    }

    /**
     * Método auxiliar para mapear una entidad AsignacionAsignaturaEntity a DTO
     */
    private AsignacionAsignaturaDTO mapearAsignacionADTO(AsignacionAsignaturaEntity asignacion) {
        AsignacionAsignaturaDTO dto = new AsignacionAsignaturaDTO();
        dto.setId(asignacion.getId());
        dto.setAsignatura(asignaturaMapper.toDTO(asignacion.getAsignatura()));
        dto.setCompetencia(competenciaAsignaturaMapper.toDTO(asignacion.getCompetencia()));
        dto.setDocente(getDocenteMapper.toDto(asignacion.getDocente()));
        dto.setPeriodo(asignacion.getPeriodo());
        return dto;
    }
}