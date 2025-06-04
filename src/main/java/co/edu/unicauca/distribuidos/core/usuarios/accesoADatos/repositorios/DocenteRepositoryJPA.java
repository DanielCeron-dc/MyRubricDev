package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;

@Repository
public interface DocenteRepositoryJPA extends JpaRepository<DocenteEntity, Integer>  {
    
}