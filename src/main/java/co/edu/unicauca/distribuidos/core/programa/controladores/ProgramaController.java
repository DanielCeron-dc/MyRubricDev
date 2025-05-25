package co.edu.unicauca.distribuidos.core.programa.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/programa")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProgramaController {

    @GetMapping()
    public String getPrograma() {
        return "Hola programa";
    }
}
