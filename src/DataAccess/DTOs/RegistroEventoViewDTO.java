package DataAccess.DTOs;

public class RegistroEventoViewDTO {
    private Integer idRegistroEvento;
    private Integer idTipoEvento;
    private String  NombreTipoEvento;   // <-- viene del JOIN
    private Integer idUsuario;
    private Integer idCasillero;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public RegistroEventoViewDTO() {}

    public Integer getIdRegistroEvento() { return idRegistroEvento; }
    public void setIdRegistroEvento(Integer idRegistroEvento) { this.idRegistroEvento = idRegistroEvento; }

    public Integer getIdTipoEvento() { return idTipoEvento; }
    public void setIdTipoEvento(Integer idTipoEvento) { this.idTipoEvento = idTipoEvento; }

    public String getNombreTipoEvento() { return NombreTipoEvento; }
    public void setNombreTipoEvento(String nombreTipoEvento) { NombreTipoEvento = nombreTipoEvento; }

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

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento   : " + idRegistroEvento
            + "\n idTipoEvento       : " + idTipoEvento
            + "\n NombreTipoEvento   : " + NombreTipoEvento
            + "\n idUsuario          : " + idUsuario
            + "\n idCasillero        : " + idCasillero
            + "\n Estado             : " + Estado
            + "\n FechaCreacion      : " + FechaCreacion
            + "\n FechaModificacion  : " + FechaModificacion
            + "\n ----------------------------";
    }
}

