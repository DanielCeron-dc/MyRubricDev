package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.usuarios.jwt.JwtTokenProvider;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.AuthResponseDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.RegisterRequestDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.RegisterResponseDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final UsuarioMapper userMapper;

    @Override
    public AuthResponseDTO login(String username, String password) {
        log.info("Processing login request for user: {}", username);

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);
        log.info("Successfully authenticated user: {}", username);

        // Create response with token
        AuthResponseDTO response = new AuthResponseDTO(
                jwt,
                tokenProvider.getJwtExpirationMs(),
                username);

        return response;
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Processing registration request for username: {}, role: {}",
                registerRequest.getUsername(), registerRequest.getRol());

        // Check if username is already taken
        if (userService.existsByUsername(registerRequest.getUsername())) {
            log.warn("Registration failed - username already exists: {}", registerRequest.getUsername());
            throw new BusinessException(
                    ErrorCode.RESOURCE_ALREADY_EXISTS,
                    HttpStatus.CONFLICT,
                    "Username '" + registerRequest.getUsername() + "' is already taken");
        }

        // Create new user
        UsuarioEntity user = userService.registerUser(registerRequest);
        log.info("User registered successfully: {}", registerRequest.getUsername());

        return new RegisterResponseDTO("Usuario registrado con éxito", userMapper.toDTO(user));
    }

}
