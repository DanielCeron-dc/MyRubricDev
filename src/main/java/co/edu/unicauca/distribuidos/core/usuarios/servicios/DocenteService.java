package co.edu.unicauca.distribuidos.core.usuarios.servicios;

import java.util.List;

import co.edu.unicauca.distribuidos.core.usuarios.accesoadatos.modelos.DocenteEntity;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.CrearDocenteDTO;
import co.edu.unicauca.distribuidos.core.usuarios.servicios.dto.DocenteDTO;

public interface DocenteService {

    /**
     * Guarda un docente nuevo.
     * @param docente DTO con datos del docente
     * @return DocenteDTO con la información creada
     */
    DocenteDTO save(CrearDocenteDTO docente);

    /**
     * Lista todos los docentes activos.
     * @return Lista de DocenteDTO
     */
    List<DocenteDTO> listarDocentesActivos();

    /**
     * Desactiva un docente (cambia su estado 'activo' a false).
     * @param id ID del docente
     * @return true si se desactivó correctamente
     */
    boolean desactivarDocente(Integer id);

    /**
     * Edita la información de un docente existente.
     * @param id ID del docente
     * @param docenteDTO Datos nuevos del docente
     * @return DocenteDTO actualizado
     */
    DocenteDTO editarDocente(Integer id, CrearDocenteDTO docenteDTO);

    /**
     * Lista todos los evaluadores externos activos.
     * @return Lista de DocenteDTO
     */
    //List<DocenteDTO> listarEvaluadoresExternos();

    /**
     * Crea un evaluador externo (tipoDocente = null).
     * @param evaluador DTO del evaluador
     * @return DocenteDTO creado
     */
    //DocenteDTO saveEvaluador(CrearDocenteDTO evaluador);

    /**
     * Desactiva un evaluador externo.
     * @param id ID del evaluador
     * @return true si se desactivó correctamente
     */
    //boolean desactivarEvaluador(Integer id);
}
