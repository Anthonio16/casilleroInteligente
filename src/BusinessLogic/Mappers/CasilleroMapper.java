package BusinessLogic.Mappers;

import BusinessLogic.Entities.Casillero;
import BusinessLogic.Entities.EstadoCasillero;
import DataAccess.DTOs.CasilleroDTO;

public class CasilleroMapper {

    // BD: 1=Ready to Unlock, 2=Locked
    public static EstadoCasillero idToEstado(Integer idEstado) {
        if (idEstado == null) return EstadoCasillero.READY_TO_UNLOCK;
        return (idEstado == 2) ? EstadoCasillero.LOCKED : EstadoCasillero.READY_TO_UNLOCK;
    }

    public static int estadoToId(EstadoCasillero estado) {
        if (estado == null) return 1;
        return (estado == EstadoCasillero.LOCKED) ? 2 : 1;
    }

    public static Casillero toEntity(CasilleroDTO dto) {
        if (dto == null) return null;
        return new Casillero(
            dto.getIdCasillero(),
            dto.getIdEstudiante(),
            idToEstado(dto.getIdEstadoCasillero()),
            dto.getIntentosFallidos() == null ? 0 : dto.getIntentosFallidos(),
            dto.getEstado()
        );
    }
}


