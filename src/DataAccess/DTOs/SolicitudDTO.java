package DataAccess.DTOs;

public class SolicitudDTO {
    private Integer idSolicitud;
    private Integer idCasillero;
    private Integer idAdmin;
    private Integer idEstadoSolicitud;
    private Integer idEstudianteSolicitante;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;
    
    
    public SolicitudDTO() {}
    
    public SolicitudDTO(Integer idCasillero, Integer idAdmin, Integer idEstadoSolicitud) {
        this.idSolicitud = 0;
        this.idCasillero = idCasillero;
        this.idAdmin = idAdmin;
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.Estado = "A";
    }
    
    public SolicitudDTO(Integer idSolicitud, Integer idCasillero, Integer idAdmin, Integer idEstadoSolicitud,
            String estado, String fechaCreacion, String fechaModificacion) {
        this.idSolicitud = idSolicitud;
        this.idCasillero = idCasillero;
        this.idAdmin = idAdmin;
        this.idEstadoSolicitud = idEstadoSolicitud;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }
    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public Integer getIdAdmin() { return idAdmin; }
    public void setIdAdmin(Integer idAdmin) { this.idAdmin = idAdmin; }

    public Integer getIdEstadoSolicitud() { return idEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) { this.idEstadoSolicitud = idEstadoSolicitud; }

    public Integer getIdEstudianteSolicitante() {
    return idEstudianteSolicitante;
    }

    public void setIdEstudianteSolicitante(Integer idEstudianteSolicitante) {
    this.idEstudianteSolicitante = idEstudianteSolicitante;
    }
    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idSolicitud        : " + idSolicitud
            + "\n idCasillero        : " + idCasillero
            + "\n idAdmin            : " + idAdmin
            + "\n idEstadoSolicitud  : " + idEstadoSolicitud
            + "\n Estado             : " + Estado
            + "\n FechaCreacion      : " + FechaCreacion
            + "\n FechaModificacion  : " + FechaModificacion
            + "\n ----------------------------";
    }
}
