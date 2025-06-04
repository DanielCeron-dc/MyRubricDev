package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignacionCompDocenteEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;     
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.AsignacionCompDocenteRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.AsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.CompetenciaAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignacionService;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsignacionServiceImpl implements AsignacionService {

    private final AsignacionCompDocenteRepository asignacionRepository;
    private final UserRepository usuarioRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final CompetenciaAsignaturaRepository competenciaRepository;
    private final PeriodoAcademicoService periodoService;

    @Override
    @Transactional
    public AsignacionCompDocenteEntity asignarDocenteCompetencia(Long docenteId, Long asignaturaId, Long competenciaId) {
        UsuarioEntity docente = usuarioRepository.findById(docenteId)
                .orElseThrow(() -> new EntityNotFoundException("Docente no encontrado"));
        
        AsignaturaEntity asignatura = asignaturaRepository.findById(asignaturaId)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada"));
        
        CompetenciaAsignaturaEntity competencia = competenciaRepository.findById(competenciaId)
                .orElseThrow(() -> new EntityNotFoundException("Competencia no encontrada"));
        
        String periodoActual = periodoService.obtenerPeriodoActual();
        
        if (asignacionRepository.existsByUsuarioAndAsignaturaAndPeriodo(
                docente, asignatura, periodoActual)) {
            throw new IllegalOperationException("El docente ya está asignado a esta asignatura en el periodo actual");
        }
        
        AsignacionCompDocenteEntity asignacion = new AsignacionCompDocenteEntity();
        asignacion.setUsuario(docente);
        asignacion.setAsignatura(asignatura);
        asignacion.setCompetenciaAsignatura(competencia);
        asignacion.setPeriodo(periodoActual);
        
        return asignacionRepository.save(asignacion);
    }
}