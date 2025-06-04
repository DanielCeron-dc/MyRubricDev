package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.dto.CompetenciaAsignaturaDTO;
import co.edu.unicauca.distribuidos.core.asignaturas.dto.request.CompetenciaAsignaturaRequestDTO;

public interface CompetenciaService {
    /**
     * Crea una nueva competencia para una asignatura.
     *
     * @param request Contiene los datos necesarios para crear la competencia.
     * @return La entidad de la competencia creada.
     */
    CompetenciaAsignaturaDTO crearCompetencia(CompetenciaAsignaturaRequestDTO request);

    /**
     * Actualiza una competencia existente.
     *
     * @param request Contiene los datos necesarios para actualizar la competencia.
     * @return La entidad de la competencia actualizada.
     */
    CompetenciaAsignaturaDTO actualizarCompetencia(CompetenciaAsignaturaRequestDTO request);
}