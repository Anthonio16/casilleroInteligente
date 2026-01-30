package DataAccess.DTOs;

public class EstadoSolicitudDTO {
    private Integer idEstadoSolicitud;
    private String  Nombre;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    
    public EstadoSolicitudDTO() {}
    
    public EstadoSolicitudDTO(String nombre, String descripcion, String estado) {
        Nombre = nombre;
        Descripcion = descripcion;
        Estado = estado;
    }
    public EstadoSolicitudDTO(String nombre, String descripcion) {
        this.idEstadoSolicitud = 0;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Estado = "A";
    }

    public Integer getIdEstadoSolicitud() { return idEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) { this.idEstadoSolicitud = idEstadoSolicitud; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getDescripcion() { return Descripcion; }
    public void setDescripcion(String descripcion) { Descripcion = descripcion; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idEstadoSolicitud : " + getIdEstadoSolicitud()
            + "\n Nombre            : " + getNombre()
            + "\n Descripcion       : " + getDescripcion()
            + "\n Estado            : " + getEstado()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModificacion : " + getFechaModificacion()
            + "\n ----------------------------";
    }
}


