package co.edu.unicauca.distribuidos.core.programa.servicios;

import java.util.List;
import co.edu.unicauca.distribuidos.core.programa.servicios.dto.*;

public interface ProgramaService {
    List<CompetenciaProgramaDTO> listarCompetencias();

    CompetenciaProgramaDTO crearCompetencia(CrearEditarCompetenciaProgramaDTO dto);

    CompetenciaProgramaDTO editarCompetencia(Integer id, CrearEditarCompetenciaProgramaDTO dto);

    List<ResultadoProgramaDTO> listarResultados();

    ResultadoProgramaDTO crearResultado(CrearEditarResultadoProgramaDTO dto);

    ResultadoProgramaDTO editarResultado(Integer id, CrearEditarResultadoProgramaDTO dto);
}