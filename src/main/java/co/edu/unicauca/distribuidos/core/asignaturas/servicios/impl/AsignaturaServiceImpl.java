package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignaturaService;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignaturaRepository;
@Service
@RequiredArgsConstructor
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    @Override
    @Transactional
    public AsignaturaEntity crearAsignatura(AsignaturaRequestDTO request) {
        AsignaturaEntity asignatura = new AsignaturaEntity();
        asignatura.setNombre(request.getNombre());
        asignatura.setCreditos(request.getCreditos());
        asignatura.setActiva(true);
        asignatura.setSemestre(request.getSemestre());
        asignatura.setObjetivos(request.getObjetivos());
        asignatura.setCodigo(request.getCodigo());
        return asignaturaRepository.save(asignatura);
    }

    @Override
    @Transactional
    public AsignaturaEntity actualizarAsignatura(AsignaturaRequestDTO request) {
        AsignaturaEntity existente = asignaturaRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Asignatura no encontrada"));

        return asignaturaRepository.save(existente);
    }
}