package co.edu.unicauca.distribuidos.core.asignaturas.servicios.impl;

import co.edu.unicauca.distribuidos.core.asignaturas.accesoADatos.repositorios.ConfiguracionPeriodoRepository;
import co.edu.unicauca.distribuidos.core.asignaturas.servicios.PeriodoAcademicoService;
import co.edu.unicauca.distribuidos.core.errores.BusinessException;
import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PeriodoAcademicoServiceImpl implements PeriodoAcademicoService {

    private final ConfiguracionPeriodoRepository configuracionPeriodoRepository;

    @Override
    public String getPeriodoAcademico() {
        return configuracionPeriodoRepository.findByActivoTrue()
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "No se ha cargado la configuración del periodo"
                )).getPeriodo();
    }
}
