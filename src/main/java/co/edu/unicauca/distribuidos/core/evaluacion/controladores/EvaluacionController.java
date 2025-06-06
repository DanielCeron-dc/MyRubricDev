package co.edu.unicauca.distribuidos.core.evaluacion.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/evaluacion")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class EvaluacionController {

    // get map api/usuarios and api/usuarios/
    @GetMapping()
    public String getEvaluacion() {
        return "Hola evaluacion";
    }
}
