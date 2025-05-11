package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ReservaRepository;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ReservaDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.SalonDTORespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<ReservaDTORespuesta> findAll() {
        return reservaRepository.findAll()
            .map(coll -> coll.stream()
                .map(this::toDTO)
                .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    }

    @Override
    public Optional<ReservaDTORespuesta> findById(Integer id) {
        return reservaRepository.findById(id).map(this::toDTO);
    }

    @Override
    public ReservaDTORespuesta save(ReservaDTOPeticion dto) {
        ReservaEntity entity = toEntity(dto);
        ReservaEntity saved = reservaRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public Optional<ReservaDTORespuesta> update(Integer id, ReservaDTOPeticion dto) {
        ReservaEntity entity = toEntity(dto);
        System.out.println("ID: " + id);
        System.out.println("Entity: " + entity);
        System.out.println("status: " + entity.getStatus());
        return reservaRepository.update(id, entity)
                .map(this::toDTO);
    }

    @Override
    public boolean delete(Integer id) {
        return reservaRepository.delete(id);
    }

    private ReservaDTORespuesta toDTO(ReservaEntity e) {
        SalonDTORespuesta salonDto = new SalonDTORespuesta(e.getObjSalon().getId(), 
            e.getObjSalon().getCode(), e.getObjSalon().getName(), e.getObjSalon().getLocation());
        return new ReservaDTORespuesta(
            e.getId(),
            e.getName(),
            e.getSurname(),
            e.getLocation(),
            e.getPeopleAmount(),
            e.getDate(),
            e.getStartTime(),
            e.getEndTime(),
            e.getStatus(),
            salonDto
        );
    }

    private ReservaEntity toEntity(ReservaDTOPeticion dto) {
        ReservaEntity e = new ReservaEntity();
        e.setName(dto.getName());
        e.setSurname(dto.getSurname());
        e.setLocation(dto.getLocation());
        e.setPeopleAmount(dto.getPeopleAmount());
        e.setDate(new java.sql.Date(dto.getDate().getTime()));
        e.setStartTime(dto.getStartTime());
        e.setEndTime(dto.getEndTime());
        e.setStatus(dto.getStatus());
        SalonEntity s = new SalonEntity();

        s.setId(dto.getObjSalon().getId());
        e.setObjSalon(s);
        return e;
    }
}