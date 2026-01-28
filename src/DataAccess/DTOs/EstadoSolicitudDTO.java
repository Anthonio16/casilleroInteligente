package DataAccess.DTOs;

public class EstadoSolicitudDTO {
    private Integer IdEstadoSolicitud;
    private String  Nombre;
    private String  Descripcion;
    private String  FechaCreacion;
    private String  FechaModificacion;

    
    public EstadoSolicitudDTO() {}
    
    public EstadoSolicitudDTO(String nombre, String descripcion, String estado) {
        Nombre = nombre;
        Descripcion = descripcion;
    }
    public EstadoSolicitudDTO(String nombre, String descripcion) {
        this.IdEstadoSolicitud = 0;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }

    public Integer getIdEstadoSolicitud() { return IdEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer IdEstadoSolicitud) { this.IdEstadoSolicitud = IdEstadoSolicitud; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getDescripcion() { return Descripcion; }
    public void setDescripcion(String descripcion) { Descripcion = descripcion; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdEstadoSolicitud : " + getIdEstadoSolicitud()
            + "\n Nombre            : " + getNombre()
            + "\n Descripcion       : " + getDescripcion()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModificacion : " + getFechaModificacion()
            + "\n ----------------------------";
    }
}


