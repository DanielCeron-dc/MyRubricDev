package co.edu.unicauca.distribuidos.core.asignaturas.servicios;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.modelos.CompetenciaAsignaturaEntity;

public interface CompetenciaService {
    /**
     * Crea una nueva competencia para una asignatura.
     *
     * @param request Contiene los datos necesarios para crear la competencia.
     * @return La entidad de la competencia creada.
     */
    CompetenciaAsignaturaEntity crearCompetencia(CompetenciaCrearRequest request);

    /**
     * Actualiza una competencia existente.
     *
     * @param request Contiene los datos necesarios para actualizar la competencia.
     * @return La entidad de la competencia actualizada.
     */
    CompetenciaAsignaturaEntity actualizarCompetencia(CompetenciaActualizarRequest request);
}