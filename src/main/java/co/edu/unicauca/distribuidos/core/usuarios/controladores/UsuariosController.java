package co.edu.unicauca.distribuidos.core.usuarios.controladores;

import static java.lang.Math.log;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios.DocenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5000", methods = {RequestMethod.GET})
public class UsuariosController {


    private DocenteRepository docenteRepository;

    // get map api/usuarios and api/usuarios/
    @GetMapping()
    public String getUsuarios() {
        return "Hola usuarios";
    }
//Creacion del endpoint para docentes
     @GetMapping("/docentes")
    public ResponseEntity<?> getDocentes() {
        log.info("Solicitando lista de docentes...");
        var resultado = docenteRepository.findAll();

        if (resultado.isPresent()) {
            Collection<DocenteEntity> docentes = resultado.get();
            return ResponseEntity.ok(docentes);
        } else {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay docentes
        }
    }
}