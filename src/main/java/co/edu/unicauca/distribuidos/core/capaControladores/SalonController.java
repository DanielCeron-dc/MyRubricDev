package co.edu.unicauca.distribuidos.core.capaControladores;


import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.ISalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salones")
public class SalonController {

    private final ISalonService salonService;

    @Autowired
    public SalonController(ISalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping
    public List<SalonEntity> getAll() {
        return salonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonEntity> getById(@PathVariable Integer id) {
        Optional<SalonEntity> salon = salonService.findById(id);
        return salon.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SalonEntity create(@RequestBody SalonEntity salon) {
        return salonService.save(salon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonEntity> update(@PathVariable Integer id, @RequestBody SalonEntity salon) {
        Optional<SalonEntity> updated = salonService.update(id, salon);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = salonService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}