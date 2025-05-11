// SalonDTORespuesta.java
package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonDTORespuesta {
    private Integer id;
    private String code;
    private String name;
    private String location;
}