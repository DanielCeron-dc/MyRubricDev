// co.edu.unicauca.distribuidos.core.capaNegocio.services/SalonServiceImpl.java
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceImpl implements ISalonService {

    @Autowired
    private SalonRepository salonRepository;

    @Override
    public List<SalonEntity> findAll() {
        return salonRepository.findAll().map(List::copyOf).orElseGet(List::of);
    }

    @Override
    public Optional<SalonEntity> findById(Integer id) {
        return salonRepository.findById(id);
    }

    @Override
    public SalonEntity save(SalonEntity salon) {
        return salonRepository.save(salon);
    }

    @Override
    public Optional<SalonEntity> update(Integer id, SalonEntity salon) {
        return salonRepository.findById(id).map(existing -> {
            existing.setCode(salon.getCode());
            existing.setName(salon.getName());
            existing.setLocation(salon.getLocation());
            return salonRepository.save(existing);
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (salonRepository.existsById(id)) {
            salonRepository.removeById(id);
            return true;
        }
        return false;
    }
}