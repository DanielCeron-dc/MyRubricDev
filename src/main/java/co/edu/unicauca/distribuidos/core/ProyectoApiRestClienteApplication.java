package co.edu.unicauca.distribuidos.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for the backend API
 * Configures component scanning, entity scanning, and repository scanning
 */
@SpringBootApplication
@ComponentScan(basePackages = "co.edu.unicauca.distribuidos.core")
@EntityScan(basePackages = {
    "co.edu.unicauca.distribuidos.core.rubrica.dominio",
    "co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos",
    "co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos"
})
@EnableJpaRepositories(basePackages = {
    "co.edu.unicauca.distribuidos.core.rubrica.accesoADatos.repositorios",
    "co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios",
    "co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios"
})
@EnableTransactionManagement
public class ProyectoApiRestClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApiRestClienteApplication.class, args);
	}

}
