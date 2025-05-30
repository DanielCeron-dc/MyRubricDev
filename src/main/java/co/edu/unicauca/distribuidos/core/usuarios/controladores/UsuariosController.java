package co.edu.unicauca.distribuidos.core.usuarios.controladores;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.DocenteRepository;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.DocenteService;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5000", methods = { RequestMethod.GET })
public class UsuariosController {

    private final DocenteService docenteService;

    // get map api/usuarios and api/usuarios/
    @GetMapping()
    public String getUsuarios() {
        return "Hola usuarios";
    }

    // Creacion del endpoint para docentes
    @GetMapping("/docentes")
    public ResponseEntity<?> getDocentes() {
        // TODO: implementar la consulta de docentes
        log.info("Solicitando lista de docentes...");
        return ResponseEntity.ok("lista docentes");
    }

    @PostMapping("/docentes")
    public DocenteDTO crearDocente(@RequestBody CrearDocenteDTO docenteDTO) {
        log.info("Creando docente... {}", docenteDTO);

        DocenteDTO docente = docenteService.save(docenteDTO);
        return docente;
    }
}