package DataAccess.DTOs;

public class SolicitudDTO {
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private Integer IdAdmin;
    private Integer IdEstadoSolicitud;
    private Integer IdEstudianteSolicitante;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;
    
    
    public SolicitudDTO() {}
    
    public SolicitudDTO(Integer IdCasillero, Integer IdAdmin, Integer IdEstadoSolicitud) {
        this.IdSolicitud = 0;
        this.IdCasillero = IdCasillero;
        this.IdAdmin = IdAdmin;
        this.IdEstadoSolicitud = IdEstadoSolicitud;
        this.Estado = "A";
    }
    
    public SolicitudDTO(Integer IdSolicitud, Integer IdCasillero, Integer IdAdmin, Integer IdEstadoSolicitud,
            String estado, String fechaCreacion, String fechaModificacion) {
        this.IdSolicitud = IdSolicitud;
        this.IdCasillero = IdCasillero;
        this.IdAdmin = IdAdmin;
        this.IdEstadoSolicitud = IdEstadoSolicitud;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }
    public Integer getIdSolicitud() { return IdSolicitud; }
    public void setIdSolicitud(Integer IdSolicitud) { this.IdSolicitud = IdSolicitud; }

    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer IdCasillero) { this.IdCasillero = IdCasillero; }

    public Integer getIdAdmin() { return IdAdmin; }
    public void setIdAdmin(Integer IdAdmin) { this.IdAdmin = IdAdmin; }

    public Integer getIdEstadoSolicitud() { return IdEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer IdEstadoSolicitud) { this.IdEstadoSolicitud = IdEstadoSolicitud; }

    public Integer getIdEstudianteSolicitante() {
    return IdEstudianteSolicitante;
    }

    public void setIdEstudianteSolicitante(Integer IdEstudianteSolicitante) {
    this.IdEstudianteSolicitante = IdEstudianteSolicitante;
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
            + "\n IdSolicitud        : " + IdSolicitud
            + "\n IdCasillero        : " + IdCasillero
            + "\n IdAdmin            : " + IdAdmin
            + "\n IdEstadoSolicitud  : " + IdEstadoSolicitud
            + "\n Estado             : " + Estado
            + "\n FechaCreacion      : " + FechaCreacion
            + "\n FechaModificacion  : " + FechaModificacion
            + "\n ----------------------------";
    }
}
