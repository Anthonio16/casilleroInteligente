package DataAccess.DTOs;

public class SolicitudDTO {
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private Integer IdAdmin;
    private Integer IdEstadoSolicitud;

    private String  Estado;        // 'A' / 'X'
    private String  FechaCreacion;
    private String  FechaModifica;

    public SolicitudDTO() {}

    // Para crear (sin PK ni fechas)
    public SolicitudDTO(Integer idCasillero, Integer idAdmin, Integer idEstadoSolicitud) {
        IdSolicitud       = 0;
        IdCasillero       = idCasillero;
        IdAdmin           = idAdmin;
        IdEstadoSolicitud = idEstadoSolicitud;
        Estado            = "A";
    }

    // Completo (cuando lees de BD)
    public SolicitudDTO(Integer idSolicitud, Integer idCasillero, Integer idAdmin, Integer idEstadoSolicitud,
                        String estado, String fechaCreacion, String fechaModifica) {
        IdSolicitud       = idSolicitud;
        IdCasillero       = idCasillero;
        IdAdmin           = idAdmin;
        IdEstadoSolicitud = idEstadoSolicitud;
        Estado            = estado;
        FechaCreacion     = fechaCreacion;
        FechaModifica     = fechaModifica;
    }

    public Integer getIdSolicitud() { return IdSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { IdSolicitud = idSolicitud; }

    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer idCasillero) { IdCasillero = idCasillero; }

    public Integer getIdAdmin() { return IdAdmin; }
    public void setIdAdmin(Integer idAdmin) { IdAdmin = idAdmin; }

    public Integer getIdEstadoSolicitud() { return IdEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) { IdEstadoSolicitud = idEstadoSolicitud; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModifica() { return FechaModifica; }
    public void setFechaModifica(String fechaModifica) { FechaModifica = fechaModifica; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdSolicitud        : " + getIdSolicitud()
            + "\n IdCasillero        : " + getIdCasillero()
            + "\n IdAdmin            : " + getIdAdmin()
            + "\n IdEstadoSolicitud  : " + getIdEstadoSolicitud()
            + "\n Estado             : " + getEstado()
            + "\n FechaCreacion      : " + getFechaCreacion()
            + "\n FechaModifica      : " + getFechaModifica()
            + "\n ----------------------------";
    }
}
