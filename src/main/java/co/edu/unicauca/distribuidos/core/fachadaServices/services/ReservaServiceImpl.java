package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements IReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public List<ReservaEntity> findAll() {
        Optional<Collection<ReservaEntity>> reservasOptional = reservaRepository.findAll();
        return reservasOptional.map(ArrayList::new).orElseGet(ArrayList::new);
    }

    @Override
    public Optional<ReservaEntity> findById(Integer id) {
        return reservaRepository.findById(id);
    }

    @Override
    public ReservaEntity save(ReservaEntity reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<ReservaEntity> update(Integer id, ReservaEntity reserva) {
        return reservaRepository.update(id, reserva);
    }

    @Override
    public boolean delete(Integer id) {
        return reservaRepository.delete(id);
    }
}

