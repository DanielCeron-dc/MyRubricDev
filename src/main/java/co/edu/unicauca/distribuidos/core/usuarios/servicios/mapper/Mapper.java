package co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapper {

    @Bean
    public UsuarioMapper getUsuarioMapper() {
        return new UsuarioMapper();
    }

    @Bean
    public DocenteMapper getDocenteMapper() {
        return new DocenteMapper();
    }
}
