package co.edu.unicauca.distribuidos.core.security.controllers;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.Rol;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.AuthRequestDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.AuthResponseDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.RegisterRequestDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the authentication system
 * Tests user registration, login, and access to protected endpoints
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test user registration with valid data
     */
    @Test
    public void testRegisterCoordinador() {
        // Create registration request with unique username
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername("testuser_" + UUID.randomUUID().toString().substring(0, 8));
        registerRequest.setPassword("password123");
        registerRequest.setRol(Rol.COORDINADOR);

        // Send registration request
        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/auth/register"),
                registerRequest,
                String.class);

        // Assert successful registration
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("User registered successfully"));
    }

    /**
     * Test user login with valid credentials
     */
    @Test
    public void testLoginWithValidCredentials() {
        // Create login request with existing user
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        // Send login request
        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity(
                createURLWithPort("/auth/login"),
                loginRequest,
                AuthResponseDTO.class);

        // Assert successful login
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
        assertEquals("Bearer", response.getBody().getTokenType());
        assertTrue(response.getBody().getExpiresIn() > 0);
        assertEquals("admin", response.getBody().getUsername());
    }

    /**
     * Test user login with invalid credentials
     */
    @Test
    public void testLoginWithInvalidCredentials() {
        // Create login request with invalid credentials
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("wrongpassword");

        // Send login request
        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity(
                createURLWithPort("/auth/login"),
                loginRequest,
                AuthResponseDTO.class);

        // Assert unauthorized response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Test accessing a protected endpoint with a valid token
     */
    @Test
    public void testAccessProtectedEndpointWithValidToken() {
        // First, login to get a token
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        ResponseEntity<AuthResponseDTO> loginResponse = restTemplate.postForEntity(
                createURLWithPort("/auth/login"),
                loginRequest,
                AuthResponseDTO.class);

        // Get token from login response
        String token = loginResponse.getBody().getToken();

        // Create headers with token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Send request to protected endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/salones"),
                HttpMethod.GET,
                entity,
                String.class);

        // Assert successful access
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test accessing a protected endpoint without a token
     */
    @Test
    public void testAccessProtectedEndpointWithoutToken() {
        // Send request to protected endpoint without token
        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/api/salones"),
                String.class);

        // Assert unauthorized access
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Test token validation by manipulating the token
     */
    @Test
    public void testInvalidToken() {
        // First, login to get a token
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        ResponseEntity<AuthResponseDTO> loginResponse = restTemplate.postForEntity(
                createURLWithPort("/auth/login"),
                loginRequest,
                AuthResponseDTO.class);

        // Get token from login response and manipulate it to make it invalid
        String validToken = loginResponse.getBody().getToken();
        String invalidToken = validToken + "invalid";

        // Create headers with invalid token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(invalidToken);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Send request to protected endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/salones"),
                HttpMethod.GET,
                entity,
                String.class);

        // Assert unauthorized access
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Helper method to create URL with port
     */
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

