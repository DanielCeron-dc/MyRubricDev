// co.edu.unicauca.distribuidos.core.capaNegocio.services/ISalonService.java
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import java.util.List;
import java.util.Optional;

public interface ISalonService {
    List<SalonEntity> findAll();
    Optional<SalonEntity> findById(Integer id);
    SalonEntity save(SalonEntity salon);
    Optional<SalonEntity> update(Integer id, SalonEntity salon);
    boolean delete(Integer id);
}