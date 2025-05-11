// ReservaDTORespuesta.java
package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTORespuesta {
    private Integer id;
    private String name;
    private String surname;
    private String location;
    private Integer peopleAmount;
    private Date date;
    private String startTime;
    private String endTime;
    private String status;
    private SalonDTORespuesta objSalon;
}