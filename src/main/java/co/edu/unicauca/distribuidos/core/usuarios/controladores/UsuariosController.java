package co.edu.unicauca.distribuidos.core.usuarios.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UsuariosController {

    // get map api/usuarios and api/usuarios/
    @GetMapping()
    public String getUsuarios() {
        return "Hola usuarios";
    }

    @GetMapping("/docentes")
    public String getDocentes() {
        return "Hola docentes";
    }
}
