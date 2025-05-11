package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTORespuesta;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    List<ReservaDTORespuesta> findAll();
    Optional<ReservaDTORespuesta> findById(Integer id);
    ReservaDTORespuesta save(ReservaDTOPeticion reserva);
    Optional<ReservaDTORespuesta> update(Integer id, ReservaDTOPeticion reserva);
    boolean delete(Integer id);
}