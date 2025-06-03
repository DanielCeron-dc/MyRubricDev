package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.DocenteRepositoryJPA;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.mapper.DocenteMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.Rol;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.UsuarioEntity;
import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.repositorios.DocenteRepository;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.RegisterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final DocenteMapper docenteMapper;
    private final DocenteRepositoryJPA docenteRepositoryJPA;
    private final UserService userService;

    @Override
    public List<DocenteDTO> listarDocentesActivos() {
        List<DocenteEntity> docentes = docenteRepositoryJPA.findAll();
        return docentes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

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
        try {
            docentePersistencia = docenteRepositoryJPA.save(docenteEntity);
        } catch (Exception e) {
            // Tira error si no se puede guardar el docente, que es porque ya existe
            throw new BusinessException(ErrorCode.REGISTRATION_IDENTIFICATION_EXISTS, "Ya existe un docente con esa identificación");
        }
        return docenteMapper.toDto(docentePersistencia);
    }

    private DocenteDTO mapToDTO(DocenteEntity entity) {
        DocenteDTO dto = new DocenteDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setCorreoAcademico(entity.getCorreoAcademico());
        dto.setTipoIdentificacion(entity.getTipoIdentificacion());
        dto.setIdentificacion(entity.getIdentificacion());
        dto.setTipoDocente(entity.getTipoDocente());
        dto.setTituloAcademico(entity.getTituloAcademico());
        return dto;
    }


    /*
    // Métodos para Evaluadores (si son tratados como docentes)
    @Override
    public List<DocenteDTO> listarEvaluadoresExternos() {
        return docenteRepository.findByTipoDocenteAndActivoTrue("EVALUADOR_EXTERNO")
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    */
    /*@Override
    @Transactional
    public DocenteDTO saveEvaluador(CrearDocenteDTO dto) {
        dto.setTipoDocente("EVALUADOR_EXTERNO");
        return save(dto);
    }
    */
    @Override
    public boolean desactivarDocente(Integer id) {
        Optional<DocenteEntity> optional = docenteRepositoryJPA.findById(id);
        if (optional.isPresent()) {
            DocenteEntity entity = optional.get();
            entity.setActivo(false);
            docenteRepositoryJPA.save(entity);
        } else {
            throw new RuntimeException("Docente no encontrado con ID: " + id);
        }
        return true;
    }

    @Override
    public DocenteDTO editarDocente(Integer id, CrearDocenteDTO dto) {
        Optional<DocenteEntity> optional = docenteRepositoryJPA.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Docente no encontrado con ID: " + id);
        }

        DocenteEntity entity = optional.get();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setCorreoAcademico(dto.getCorreoAcademico());
        entity.setTipoIdentificacion(dto.getTipoIdentificacion());
        entity.setIdentificacion(dto.getIdentificacion());
        entity.setTipoDocente(dto.getTipoDocente());
        entity.setTituloAcademico(dto.getTituloAcademico());

        DocenteEntity updated = docenteRepositoryJPA.save(entity);
        return mapToDTO(updated);
    }
    /*
    @Override
    public boolean desactivarEvaluador(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desactivarEvaluador'");
    }
    */

}