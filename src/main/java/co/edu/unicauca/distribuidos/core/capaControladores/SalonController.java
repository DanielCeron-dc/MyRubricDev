package co.edu.unicauca.distribuidos.core.capaControladores;

import co.edu.unicauca.distribuidos.core.fachadaServices.services.ISalonService;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTORespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salones")
public class SalonController {

    @Autowired
    private ISalonService salonService;

    @GetMapping
    public List<SalonDTORespuesta> getAll() {
        return salonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDTORespuesta> getById(@PathVariable Integer id) {
        Optional<SalonDTORespuesta> s = salonService.findById(id);
        return s.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SalonDTORespuesta create(@RequestBody SalonDTOPeticion salon) {
        return salonService.save(salon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonDTORespuesta> update(
        @PathVariable Integer id,
        @RequestBody SalonDTOPeticion salon
    ) {
        return salonService.update(id, salon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return salonService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}