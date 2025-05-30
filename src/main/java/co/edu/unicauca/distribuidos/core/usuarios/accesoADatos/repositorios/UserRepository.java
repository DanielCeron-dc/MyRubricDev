package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.UsuarioEntity;

import java.util.Optional;

/**
 * Repository for user-related database operations
 */
@Repository
public interface UserRepository {
    
    /**
     * Find a user by their username
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<UsuarioEntity> findByUsername(String username);
    
    /**
     * Check if a user with the given username exists
     * 
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Save a user to the database
     *
     * @param usuario the user entity to save
     * @return the saved user entity
     */
    UsuarioEntity save(UsuarioEntity usuario);
}

