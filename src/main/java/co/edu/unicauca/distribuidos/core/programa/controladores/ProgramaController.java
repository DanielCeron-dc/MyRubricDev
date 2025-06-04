package co.edu.unicauca.distribuidos.core.programa.controladores;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import co.edu.unicauca.distribuidos.core.programa.servicios.dto.*;
import co.edu.unicauca.distribuidos.core.programa.servicios.ProgramaService;

@RestController
@RequestMapping("/api/programa")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProgramaController {

    private final ProgramaService programaService;

    @GetMapping("/competencias")
    public List<CompetenciaProgramaDTO> listarCompetencias() {
        return programaService.listarCompetencias();
    }

    @PostMapping("/competencia")
    public CompetenciaProgramaDTO crearCompetencia(@RequestBody CrearEditarCompetenciaProgramaDTO dto) {
        return programaService.crearCompetencia(dto);
    }

    @PutMapping("/competencia/{id}")
    public CompetenciaProgramaDTO editarCompetencia(@PathVariable Integer id, @RequestBody CrearEditarCompetenciaProgramaDTO dto) {
        return programaService.editarCompetencia(id, dto);
    }

    @GetMapping("/ras")
    public List<ResultadoProgramaDTO> listarResultados() {
        return programaService.listarResultados();
    }

    @PostMapping("/ra")
    public ResultadoProgramaDTO crearResultado(@RequestBody CrearEditarResultadoProgramaDTO dto) {
        return programaService.crearResultado(dto);
    }

    @PutMapping("/ra/{id}")
    public ResultadoProgramaDTO editarResultado(@PathVariable Integer id, @RequestBody CrearEditarResultadoProgramaDTO dto) {
        return programaService.editarResultado(id, dto);
    }
}