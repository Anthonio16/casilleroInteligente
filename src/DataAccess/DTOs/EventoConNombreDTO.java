// =========================================================
// DataAccess/DTOs/EventoConNombreDTO.java
// =========================================================
package DataAccess.DTOs;

public class EventoConNombreDTO {

    // columnas de RegistroEvento
    private Integer idRegistroEvento;
    private Integer idTipoEvento;
    private Integer idUsuario;
    private Integer idCasillero;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    // columna del JOIN con TipoEvento
    private String NombreEvento;

    public EventoConNombreDTO() {}

    public Integer getIdRegistroEvento() { return idRegistroEvento; }
    public void setIdRegistroEvento(Integer idRegistroEvento) { this.idRegistroEvento = idRegistroEvento; }

    public Integer getIdTipoEvento() { return idTipoEvento; }
    public void setIdTipoEvento(Integer idTipoEvento) { this.idTipoEvento = idTipoEvento; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    public String getNombreEvento() { return NombreEvento; }
    public void setNombreEvento(String nombreEvento) { NombreEvento = nombreEvento; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento  : " + idRegistroEvento
            + "\n idTipoEvento      : " + idTipoEvento
            + "\n NombreEvento      : " + NombreEvento
            + "\n idUsuario         : " + idUsuario
            + "\n idCasillero       : " + idCasillero
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }
}

