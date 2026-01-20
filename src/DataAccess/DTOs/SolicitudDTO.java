package DataAccess.DTOs;

public class SolicitudDTO {
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private Integer IdEstadoSolicitud;
    private String FechaCreacion;
    private String FechaModifica;
    
    public SolicitudDTO() {}
    
    public SolicitudDTO(Integer IdSolicitud, Integer IdCasillero, Integer IdEstadoSolicitud, String fechaCreacion,String fechaModifica) {
        this.IdSolicitud = IdSolicitud;
        this.IdCasillero = IdCasillero;
        this.IdEstadoSolicitud = IdEstadoSolicitud;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(Integer IdSolicitud) {
        this.IdSolicitud = IdSolicitud;
    }

    public Integer getIdCasillero() {
        return IdCasillero;
    }

    public void setIdCasillero(Integer IdCasillero) {
        this.IdCasillero = IdCasillero;
    }

    public Integer getIdEstadoSolicitud() {
        return IdEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Integer IdEstadoSolicitud) {
        this.IdEstadoSolicitud = IdEstadoSolicitud;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaModifica() {
        return FechaModifica;
    }

    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdSolicitud        :" + getIdSolicitud       ()
        + "\n IdCasillero        :" + getIdCasillero       ()
        + "\n IdEstadoSolicitud  :" + getIdEstadoSolicitud ()
        + "\n FechaCreacion      :" + getFechaCreacion     ()
        + "\n FechaModifica      :" + getFechaModifica     ();  
    }
}
