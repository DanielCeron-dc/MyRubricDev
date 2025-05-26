package co.edu.unicauca.distribuidos.core.seguridad.controladores;

import co.edu.unicauca.distribuidos.core.seguridad.servicios.AuthService;

import co.edu.unicauca.distribuidos.core.seguridad.servicios.dto.AuthRequestDTO;

import co.edu.unicauca.distribuidos.core.seguridad.servicios.dto.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticate user and generate JWT token
     * This endpoint is publicly accessible without authentication
     *
     * @param loginRequest the login request with username and password
     * @return ResponseEntity with AuthResponseDTO containing JWT token
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthRequestDTO loginRequest) {
        return ResponseEntity.ok(
                authService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    /**
     * Register a new user
     * This endpoint is publicly accessible without authentication
     *
     * @param registerRequest the registration request
     * @return ResponseEntity with status
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
