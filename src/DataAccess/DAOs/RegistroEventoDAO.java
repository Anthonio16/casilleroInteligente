package DataAccess.DAOs;
import java.util.List;

import DataAccess.DTOs.RegistroEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class RegistroEventoDAO extends DataHelperSQLiteDAO<RegistroEventoDTO> {
    public RegistroEventoDAO() throws AppException {
        super(RegistroEventoDTO.class, "RegistroEvento", "IdRegistroEvento");
    }
    
    public void crearEvento( int IdTipoEvento, int idUsuario, int idCasillero) throws AppException {
        String query = "INSERT INTO RegistroEvento (IdTipoEvento, IdUsuario, IdCasillero, FechaHora) VALUES (?, ?, ?, datetime('now'))";
        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, IdTipoEvento);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearEvento");
        }
    }

    public List<RegistroEventoDTO> obtenerEventosCasillero(int idCasillero) throws AppException {
        String query = "SELECT * FROM RegistroEvento WHERE IdCasillero = ? ORDER BY FechaHora DESC";
        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosCasillero");
        }
    }

    public List<RegistroEventoDTO> obtenerEventosUsuario(int idUsuario) throws AppException {
        String query = "SELECT * FROM RegistroEvento WHERE IdUsuario = ? ORDER BY FechaHora DESC";
        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosUsuario");
        }
    }

    public RegistroEventoDTO obtenerUltimoEventoCasillero(int idCasillero) throws AppException {
        String query = "SELECT * FROM RegistroEvento WHERE IdCasillero = ? ORDER BY FechaHora DESC LIMIT 1";
        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerUltimoEventoCasillero");
        }
    }

}