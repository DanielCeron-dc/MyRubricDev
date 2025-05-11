package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonEntity {
    private Integer id;
    private String code;
    private String name; 
    private String location;
}

