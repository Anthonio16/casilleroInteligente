package DataAccess.DTOs;

public class EstadoCasilleroDTO {

    private Integer IdEstadoCasillero;
    private String  Nombre;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public EstadoCasilleroDTO() {}

    public EstadoCasilleroDTO(String nombre, String descripcion) {
        this.IdEstadoCasillero = 0;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Estado = "A";
    }

    public EstadoCasilleroDTO(Integer IdEstadoCasillero, String nombre, String descripcion,String estado, String fechaCreacion, String fechaModifica) {
        this.IdEstadoCasillero = IdEstadoCasillero;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Estado = estado;
        this.FechaCreacion = fechaCreacion;
        this.FechaModificacion = fechaModifica;
    }

    public Integer getIdEstadoCasillero() { return IdEstadoCasillero; }
    public void setIdEstadoCasillero(Integer IdEstadoCasillero) { this.IdEstadoCasillero = IdEstadoCasillero; }
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
            + "\n IdEstadoCasillero : " + getIdEstadoCasillero()
            + "\n Nombre            : " + getNombre()
            + "\n Descripcion       : " + getDescripcion()
            + "\n Estado            : " + getEstado()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModificacion     : " + getFechaModificacion()
            + "\n ----------------------------";
    }
}


