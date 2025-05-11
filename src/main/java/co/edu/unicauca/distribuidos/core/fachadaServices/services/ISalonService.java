package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTORespuesta;
import java.util.List;
import java.util.Optional;

public interface ISalonService {
    List<SalonDTORespuesta> findAll();
    Optional<SalonDTORespuesta> findById(Integer id);
    SalonDTORespuesta save(SalonDTOPeticion salon);
    Optional<SalonDTORespuesta> update(Integer id, SalonDTOPeticion salon);
    boolean delete(Integer id);
}