package co.edu.unicauca.distribuidos.core.capaControladores;

import co.edu.unicauca.distribuidos.core.fachadaServices.services.IReservaService;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTORespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaRestController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping
    public List<ReservaDTORespuesta> getAll() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTORespuesta> getById(@PathVariable Integer id) {
        return reservaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservaDTORespuesta create(@RequestBody ReservaDTOPeticion reserva) {
        return reservaService.save(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTORespuesta> update(
        @PathVariable Integer id,
        @RequestBody ReservaDTOPeticion reserva
    ) {
        return reservaService.update(id, reserva)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return reservaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}