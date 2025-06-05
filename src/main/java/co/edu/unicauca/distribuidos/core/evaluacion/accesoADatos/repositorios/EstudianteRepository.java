package co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.repositorios;


import co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.modelos.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Integer> {
}
