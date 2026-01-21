package DataAccess.DAOs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataAccess.DTOs.TipoEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class TipoEventoDAO extends DataHelperSQLiteDAO<TipoEventoDTO> {

    public TipoEventoDAO() throws AppException {
        super(TipoEventoDTO.class, "TipoEvento", "IdTipoEvento");
    }
    
    public TipoEventoDTO findByName(String nombreEvento) throws AppException {
        String query = "SELECT * FROM TipoEvento WHERE NombreEvento = ?";
        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nombreEvento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (SQLException e) {
            throw new AppException(null, e, getClass(), "findByName");
        }
    }

    public List<TipoEventoDTO> findAll() throws AppException {
        return readAll();
    }
}
