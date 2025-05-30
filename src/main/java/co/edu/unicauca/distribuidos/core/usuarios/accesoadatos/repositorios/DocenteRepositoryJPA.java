package co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;

@Repository
public interface DocenteRepositoryJPA extends JpaRepository<DocenteEntity, Integer>  {
    
}