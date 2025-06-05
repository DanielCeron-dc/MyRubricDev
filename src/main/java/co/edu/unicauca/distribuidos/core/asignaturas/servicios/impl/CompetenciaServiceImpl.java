package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.dto.request.CompetenciaAsignaturaRequestDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.CompetenciaAsignaturaRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.CompetenciaService;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.mapper.CompetenciaAsignaturaMapper;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.CompetenciaProgramaEntity;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios.CompetenciaProgramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetenciaServiceImpl implements CompetenciaService {


    private final CompetenciaAsignaturaRepository competenciaAsignaturaRepository;
    private final CompetenciaProgramaRepository competenciaProgramaRepository;
    private final CompetenciaAsignaturaMapper compMapper;

    @Override
    public CompetenciaAsignaturaDTO crearCompetencia(CompetenciaAsignaturaRequestDTO request) {

        CompetenciaProgramaEntity competencia = competenciaProgramaRepository.findById(request.getCompetenciaProgramaId()).orElse(null);
        if (competencia == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "No existe una competencia de programa con el id" + request.getCompetenciaProgramaId());
        }
        if (competenciaAsignaturaRepository.existsByCodigo(request.getCodigo())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Ya existe una competencia con el codigo " + request.getCodigo());
        }
        CompetenciaAsignaturaEntity createEntity = compMapper.toEntity(request, competencia);
        CompetenciaAsignaturaEntity persistedEntity = competenciaAsignaturaRepository.save(createEntity);
        return compMapper.toDTO(persistedEntity);
    }

    @Override
    public CompetenciaAsignaturaDTO actualizarCompetencia(CompetenciaAsignaturaRequestDTO request) {
        return null;
    }
}
