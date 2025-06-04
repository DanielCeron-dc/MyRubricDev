package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.CompetenciaAsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.CompetenciaAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.CompetenciaService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.CompetenciaAsignaturaMapper;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetenciaServiceImpl implements CompetenciaService {


    private final CompetenciaAsignaturaRepository competenciaAsignaturaRepository;
    private final CompetenciaAsignaturaMapper compMapper;


    @Override
    public CompetenciaAsignaturaDTO crearCompetencia(CompetenciaAsignaturaRequestDTO request) {
        if (competenciaAsignaturaRepository.existsByCodigo(request.getCodigo())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Ya existe una competencia con el codigo " + request.getCodigo());
        }
        CompetenciaAsignaturaEntity createEntity = compMapper.toEntity(request);
        CompetenciaAsignaturaEntity persistedEntity = competenciaAsignaturaRepository.save(createEntity);
        return compMapper.toDTO(persistedEntity);
    }

    @Override
    public CompetenciaAsignaturaDTO actualizarCompetencia(CompetenciaAsignaturaRequestDTO request) {
        return null;
    }
}
