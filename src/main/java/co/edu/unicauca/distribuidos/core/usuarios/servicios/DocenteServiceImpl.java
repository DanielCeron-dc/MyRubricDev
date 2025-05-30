package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.Rol;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.DocenteRepositoryJPA;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.RegisterRequestDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper.DocenteMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepositoryJPA docenteRepository;

    private final UserService userService;

    private final DocenteMapper docenteMapper;

    @Override
    public DocenteDTO save(CrearDocenteDTO docente) {
        System.out.println("Creando docente desde el servicio...");
        // Crea el usuario
        System.out.println("Creando usuario desde el servicio...");

        UsuarioEntity usuario = null;
        if (userService.existsByUsername(docente.getIdentificacion())) {
            // TODO: hacer que busque si existe un docente con la misma identificación
            usuario = userService.findByUsername(docente.getIdentificacion());
        } else {
            RegisterRequestDTO registerRequest = new RegisterRequestDTO(
                    docente.getIdentificacion(),
                    docente.getIdentificacion(),
                    Rol.DOCENTE);
            System.out.println(registerRequest);

            usuario = userService.registerUser(registerRequest);
        }

        System.out.println(usuario);

        // Crea los detalles docente
        System.out.println("Creando detalles docente desde el servicio...");
        DocenteEntity docenteEntity = docenteMapper.toEntity(docente);
        docenteEntity.setUsuario(usuario);


        DocenteEntity docentePersistencia = null;
        // manage JPA exceptions
        try {
            docentePersistencia = docenteRepository.save(docenteEntity);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REGISTRATION_IDENTIFICATION_EXISTS, "Ya existe un docente con esa identificación");
        }
        return docenteMapper.toDto(docentePersistencia);
    }

}
