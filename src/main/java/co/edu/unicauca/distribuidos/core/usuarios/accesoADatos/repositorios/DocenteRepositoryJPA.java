package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;

public interface DocenteRepositoryJPA extends JpaRepository<DocenteEntity, Integer>  {
    
}
