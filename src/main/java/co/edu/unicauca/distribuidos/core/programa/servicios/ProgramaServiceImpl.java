package co.edu.unicauca.distribuidos.core.programa.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.programa.servicios.dto.*;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.modelos.*;
import co.edu.unicauca.distribuidos.core.programa.accesoADatos.repositorios.*;
import co.edu.unicauca.distribuidos.core.programa.servicios.mapper.ProgramaMapper;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {

    private final CompetenciaProgramaRepository competenciaRepo;
    private final ResultadoProgramaRepository resultadoRepo;
    private final ProgramaMapper mapper;

    @Override
    public List<CompetenciaProgramaDTO> listarCompetencias() {
        return competenciaRepo.findAllByOrderByCodigoAsc().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompetenciaProgramaDTO crearCompetencia(CrearEditarCompetenciaProgramaDTO dto) {
        System.out.println(dto);
        CompetenciaProgramaEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(competenciaRepo.save(entity));
    }

    @Override
    public CompetenciaProgramaDTO editarCompetencia(Integer id, CrearEditarCompetenciaProgramaDTO dto) {
        Optional<CompetenciaProgramaEntity> opt = competenciaRepo.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Competencia no encontrada");
        }
        CompetenciaProgramaEntity entity = opt.get();
        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        dto.setNivel(entity.getNivel().name());
        return mapper.toDTO(competenciaRepo.save(entity));
    }

    @Override
    public List<ResultadoProgramaDTO> listarResultados() {
        return resultadoRepo.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResultadoProgramaDTO crearResultado(CrearEditarResultadoProgramaDTO dto) {
        CompetenciaProgramaEntity competencia = competenciaRepo.findById(dto.getIdCompetencia())
                .orElseThrow(() -> new RuntimeException("Competencia no encontrada"));
        ResultadoAprendizajeProgramaEntity entity = mapper.toEntity(dto, competencia);
        return mapper.toDTO(resultadoRepo.save(entity));
    }

    @Override
    public ResultadoProgramaDTO editarResultado(Integer id, CrearEditarResultadoProgramaDTO dto) {
        ResultadoAprendizajeProgramaEntity entity = resultadoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));

        CompetenciaProgramaEntity competencia = competenciaRepo.findById(dto.getIdCompetencia())
                .orElseThrow(() -> new RuntimeException("Competencia no encontrada"));

        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCompetenciaPrograma(competencia);

        return mapper.toDTO(resultadoRepo.save(entity));
    }
}