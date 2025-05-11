package co.edu.unicauca.distribuidos.core.capaControladores;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IReservaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaRestController {

    private final IReservaService reservaService;

    @Autowired
    public ReservaRestController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<ReservaEntity> getAll() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaEntity> getById(@PathVariable Integer id) {
        Optional<ReservaEntity> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservaEntity create(@RequestBody ReservaEntity reserva) {
        return reservaService.save(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaEntity> update(@PathVariable Integer id, @RequestBody ReservaEntity reserva) {
        Optional<ReservaEntity> updated = reservaService.update(id, reserva);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = reservaService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}