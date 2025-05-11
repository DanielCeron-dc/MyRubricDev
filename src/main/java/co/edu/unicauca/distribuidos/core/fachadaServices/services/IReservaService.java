package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ReservaEntity;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    /**
     * Retrieves all reservas.
     * @return list of reservations, or empty list if none.
     */
    List<ReservaEntity> findAll();

    /**
     * Retrieves a reserva by its ID.
     * @param id the reservation ID
     * @return an Optional containing the reserva if found, or empty if not.
     */
    Optional<ReservaEntity> findById(Integer id);

    /**
     * Creates a new reserva.
     * @param reserva the reserva to create
     * @return the created reserva with generated ID.
     */
    ReservaEntity save(ReservaEntity reserva);

    /**
     * Updates an existing reserva.
     * @param id the ID of the reserva to update
     * @param reserva the reserva data to update
     * @return an Optional containing the updated reserva if the update succeeded, or empty if not found.
     */
    Optional<ReservaEntity> update(Integer id, ReservaEntity reserva);

    /**
     * Deletes a reserva by its ID.
     * @param id the reservation ID
     * @return true if deleted successfully, false if not found.
     */
    boolean delete(Integer id);
}