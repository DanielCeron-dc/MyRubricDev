package co.edu.unicauca.distribuidos.core.security.service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.security.dto.RegisterRequestDTO;

/**
 * Service interface for user-related operations
 */
public interface UserService {
    
    /**
     * Register a new user
     *
     * @param registerRequest the registration request data
     * @return the created user entity
     */
    UsuarioEntity registerUser(RegisterRequestDTO registerRequest);
    
    /**
     * Check if a user with the given username exists
     *
     * @param username the username to check
     * @return true if the username is already taken, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Find a user by their username
     *
     * @param username the username to search for
     * @return the user entity if found, null otherwise
     */
    UsuarioEntity findByUsername(String username);
}

