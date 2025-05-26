package co.edu.unicauca.distribuidos.core.seguridad.servicios;



import co.edu.unicauca.distribuidos.core.seguridad.servicios.dto.AuthResponseDTO;
import co.edu.unicauca.distribuidos.core.seguridad.servicios.dto.RegisterRequestDTO;
import co.edu.unicauca.distribuidos.core.seguridad.servicios.dto.RegisterResponseDTO;



public interface AuthService {

    /**
     * Inicio de sesion
     * @param username nombre de usuario
     * @param password contraseña del usuarios
     * @return
     */
    public AuthResponseDTO login(String username, String password);

    /**
     * Registro de usuario
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return 
     */
    public RegisterResponseDTO register(RegisterRequestDTO registerRequest);
}
