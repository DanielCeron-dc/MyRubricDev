package co.edu.unicauca.distribuidos.core.security.service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.UserRepository;
import co.edu.unicauca.distribuidos.core.security.dto.RegisterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementation of UserService and UserDetailsService interfaces
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // Use constructor injection with @Lazy for PasswordEncoder
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Load a user by username for Spring Security authentication
     *
     * @param username the username to search for
     * @return the UserDetails object for the given username
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        return user;
    }

    /**
     * Register a new user
     *
     * @param registerRequest the registration request data
     * @return the created user entity
     */
    @Override
    public UsuarioEntity registerUser(RegisterRequestDTO registerRequest) {
        // Create new user entity
        UsuarioEntity newUser = UsuarioEntity.builder()
                .username(registerRequest.getUsername())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .rol(registerRequest.getRol())
                .asignacionesDocente(new ArrayList<>())
                .evaluaciones(new ArrayList<>())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        
        // Save user to database
        return userRepository.save(newUser);
    }

    /**
     * Check if a user with the given username exists
     *
     * @param username the username to check
     * @return true if the username is already taken, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Find a user by their username
     *
     * @param username the username to search for
     * @return the user entity if found, null otherwise
     */
    @Override
    public UsuarioEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

