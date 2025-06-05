package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.AsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.AsignaturaMapper;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.AsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.AsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.AsignaturaService;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.AsignaturaRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final AsignaturaMapper asignaturaMapper;

    @Override
    @Transactional
    public AsignaturaDTO crearAsignatura(AsignaturaRequestDTO request) {
        if (asignaturaRepository.findByCodigo(request.getCodigo()).isPresent()) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Ya existe una asignatura con el codigo " + request.getCodigo());
        }
        AsignaturaEntity asignatura = asignaturaMapper.toEntity(request);
        AsignaturaEntity persisted = asignaturaRepository.save(asignatura);
        return asignaturaMapper.toDTO(persisted);
    }

    @Override
    @Transactional
    public AsignaturaDTO actualizarAsignatura(AsignaturaRequestDTO request) {
        AsignaturaEntity existente = asignaturaRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Asignatura no encontrada"));
        AsignaturaEntity asignatura = asignaturaMapper.toEntity(request);
        AsignaturaEntity persisted = asignaturaRepository.save(asignatura);

        return asignaturaMapper.toDTO(persisted);
    }

    @Override
    public List<AsignaturaDTO> listarAsignaturas() {
        List<AsignaturaEntity> asignaturas = asignaturaRepository.findAll();
        List<AsignaturaDTO> asignaturasDTO = new LinkedList<>();
        for (AsignaturaEntity asignatura : asignaturas) {
            asignaturasDTO.add(asignaturaMapper.toDTO(asignatura));
        }
        return asignaturasDTO;
    }
}