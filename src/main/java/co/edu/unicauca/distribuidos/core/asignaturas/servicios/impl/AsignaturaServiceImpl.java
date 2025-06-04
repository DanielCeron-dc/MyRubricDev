package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.AsignaturaRequest;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignaturaService;
import co.edu.unicauca.distribuidos.core.asignaturas.repositorios.AsignaturaRepository;
@Service
@RequiredArgsConstructor
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AsignaturaEntity crearAsignatura(AsignaturaRequest request) {
        AsignaturaEntity asignatura = modelMapper.map(request, AsignaturaEntity.class);
        return asignaturaRepository.save(asignatura);
    }

    @Override
    @Transactional
    public AsignaturaEntity actualizarAsignatura(AsignaturaRequest request) {
        AsignaturaEntity existente = asignaturaRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada"));
        
        modelMapper.map(request, existente);
        return asignaturaRepository.save(existente);
    }
}