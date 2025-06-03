package co.edu.unicauca.distribuidos.core.usuarios.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.DocenteRepository;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.TipoDocente;
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
@CrossOrigin(origins = "http://localhost:5000")
public class UsuariosController {

    private final DocenteService docenteService;
    //definir metodos en el servicio, no en el controlador
    // Prueba
    @GetMapping()
    public String getUsuarios() {
        return "Hola usuarios";
    }

    // ============================= DOCENTES =============================

    @GetMapping("/docentes")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<List<DocenteDTO>> getDocentes() {
        log.info("Listando docentes activos...");
        List<DocenteDTO> docentes = docenteService.listarDocentesActivos();
        return ResponseEntity.ok(docentes);
    }
    @PostMapping("/docentes")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<DocenteDTO> crearDocente(@RequestBody CrearDocenteDTO dto) {
        DocenteDTO creado = docenteService.save(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/docentes/{id}")
    public ResponseEntity<DocenteDTO> editarDocente(@PathVariable Integer id, @RequestBody CrearDocenteDTO dto) {
        DocenteDTO actualizado = docenteService.editarDocente(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/docentes/{id}")
    public ResponseEntity<Void> desactivarDocente(@PathVariable Integer id) {
        docenteService.desactivarDocente(id);
        return ResponseEntity.noContent().build();
    }
}

