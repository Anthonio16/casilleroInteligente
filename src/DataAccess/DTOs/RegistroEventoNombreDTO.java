package DataAccess.DTOs;

public class RegistroEventoNombreDTO {
    private Integer idRegistroEvento;
    private Integer idTipoEvento;
    private String  Nombre; // Nombre del TipoEvento
    private Integer idUsuario;
    private Integer idCasillero;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public RegistroEventoNombreDTO() {}

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento  : " + idRegistroEvento
            + "\n idTipoEvento      : " + idTipoEvento
            + "\n NombreTipoEvento  : " + Nombre
            + "\n idUsuario         : " + idUsuario
            + "\n idCasillero       : " + idCasillero
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }

    // getters/setters (genera con tu IDE)
}

