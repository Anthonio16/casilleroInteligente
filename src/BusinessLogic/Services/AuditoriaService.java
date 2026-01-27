package BusinessLogic.Services;

import DataAccess.DAOs.AuditoriaDAO;
import DataAccess.DTOs.AuditoriaDTO;
import Infrastructure.AppException;
import java.util.List;

public class AuditoriaService {

    private final AuditoriaDAO dao;

    public AuditoriaService() throws AppException {
        this.dao = new AuditoriaDAO();
    }

    public List<AuditoriaDTO> auditoriaAdmin(int limit) throws AppException {
        return dao.listarAdmin(limit);
    }

    public List<AuditoriaDTO> auditoriaAdminPorCasillero(int idCasillero, int limit) throws AppException {
        return dao.listarAdminPorCasillero(idCasillero, limit);
    }

    public List<AuditoriaDTO> auditoriaEstudiante(int idEstudiante, int limit) throws AppException {
        return dao.listarEstudiante(idEstudiante, limit);
    }
}

