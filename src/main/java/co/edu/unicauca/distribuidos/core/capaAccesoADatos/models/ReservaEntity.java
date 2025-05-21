package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity {
    private Integer id;
    private String name ;
    private String surname ; 
    private String location;
    private Integer peopleAmount;
    private  String date;
    private String startTime;
    private String endTime;
    private String status;

    private SalonEntity objSalon;
}
