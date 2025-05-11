package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.SalonRepository;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTORespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

@Service
public class SalonServiceImpl implements ISalonService {

    @Autowired
    private SalonRepository salonRepository;

    @Override
    public List<SalonDTORespuesta> findAll() {
        return salonRepository.findAll()
            .map(coll -> coll.stream()
                .map(this::toDTO)
                .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    }

    @Override
    public Optional<SalonDTORespuesta> findById(Integer id) {
        return salonRepository.findById(id).map(this::toDTO);
    }

    @Override
    public SalonDTORespuesta save(SalonDTOPeticion dto) {
        SalonEntity e = toEntity(dto);
        SalonEntity saved = salonRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public Optional<SalonDTORespuesta> update(Integer id, SalonDTOPeticion dto) {
        SalonEntity e = toEntity(dto);
        return salonRepository.update(id, e).map(this::toDTO);
    }

    @Override
    public boolean delete(Integer id) {
        return salonRepository.delete(id);
    }

    private SalonDTORespuesta toDTO(SalonEntity e) {
        return new SalonDTORespuesta(
            e.getId(),
            e.getCode(),
            e.getName(),
            e.getLocation()
        );
    }

    private SalonEntity toEntity(SalonDTOPeticion dto) {
        SalonEntity e = new SalonEntity();
        e.setCode(dto.getCode());
        e.setName(dto.getName());
        e.setLocation(dto.getLocation());
        return e;
    }
}