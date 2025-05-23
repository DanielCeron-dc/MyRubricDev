package co.edu.unicauca.distribuidos.core.capaControladores;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.security.dto.AuthRequestDTO;
import co.edu.unicauca.distribuidos.core.security.dto.AuthResponseDTO;
import co.edu.unicauca.distribuidos.core.security.dto.RegisterRequestDTO;
import co.edu.unicauca.distribuidos.core.security.jwt.JwtTokenProvider;
import co.edu.unicauca.distribuidos.core.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    /**
     * Authenticate user and generate JWT token
     *
     * @param loginRequest the login request with username and password
     * @return ResponseEntity with AuthResponseDTO containing JWT token
     */
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
        log.info("Processing login request for user: {}", loginRequest.getUsername());
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = tokenProvider.generateToken(authentication);
            log.info("Successfully authenticated user: {}", loginRequest.getUsername());
            
            // Create response with token
            AuthResponseDTO response = new AuthResponseDTO(
                jwt,
                tokenProvider.getJwtExpirationMs(),
                loginRequest.getUsername()
            );

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {
            log.error("Authentication error for user {}: {}", loginRequest.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Authentication error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * Register a new user
     *
     * @param registerRequest the registration request
     * @return ResponseEntity with status
     */
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
        log.info("Processing registration request for username: {}, role: {}", 
                registerRequest.getUsername(), registerRequest.getRol());
        try {
            // Check if username is already taken
            if (userService.existsByUsername(registerRequest.getUsername())) {
                log.warn("Registration failed - username already exists: {}", registerRequest.getUsername());
                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(createErrorResponse("Username is already taken", HttpStatus.CONFLICT));
            }

            // Create new user
            UsuarioEntity user = userService.registerUser(registerRequest);
            log.info("User registered successfully: {}", registerRequest.getUsername());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                    "message", "User registered successfully",
                    "username", user.getUsername(),
                    "role", user.getRol()
                ));
        } catch (Exception e) {
            log.error("Registration error for {}: {}", registerRequest.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Error during registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    
    /**
     * Helper method to create standardized error responses
     */
    private Map<String, Object> createErrorResponse(String message, HttpStatus status) {
        return Map.of(
            "error", status.getReasonPhrase(),
            "message", message,
            "status", status.value(),
            "timestamp", java.time.LocalDateTime.now().toString()
        );
    }
}

