package co.edu.unicauca.distribuidos.core.seguridad.servicios.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapper {

    @Bean
    public UsuarioMapper getUsuarioMapper() {
        return new UsuarioMapper();
    }
}
