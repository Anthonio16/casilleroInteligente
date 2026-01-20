package DataAccess.DTOs;

public class EstadoSolicitudDTO {
    private Integer IdEstadoSolicitud;
    private String Nombre;
    private String Descripcion;
    private String FechaCreacion;
    private String FechaModifica;
    
    public EstadoSolicitudDTO() {}
    
    public EstadoSolicitudDTO(Integer IdEstadoSolicitud, String nombre, String descripcion, String fechaCreacion,String fechaModifica) {
        this.IdEstadoSolicitud = IdEstadoSolicitud;
        Nombre = nombre;
        Descripcion = descripcion;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }
        
    public Integer getIdEstadoSolicitud() {
        return IdEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        IdEstadoSolicitud = idEstadoSolicitud;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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
        + "\n IdEstadoSolicitud :" + getIdEstadoSolicitud   ()
        + "\n Nombre            :" + getNombre              ()
        + "\n Descripcion       :" + getDescripcion         ()
        + "\n FechaCreacion     :" + getFechaCreacion       ()
        + "\n FechaModifica     :" + getFechaModifica       ();  
    }

}
