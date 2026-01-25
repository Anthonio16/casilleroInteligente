package DataAccess.DTOs;

public class EstadoCasilleroDTO {

    private Integer IdEstadoCasillero;
    private String  Nombre;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public EstadoCasilleroDTO() {}

    public EstadoCasilleroDTO(String nombre, String descripcion) {
        this.IdEstadoCasillero = 0;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Estado = "A";
    }

    public EstadoCasilleroDTO(Integer idEstadoCasillero, String nombre, String descripcion,
                              String estado, String fechaCreacion, String fechaModifica) {
        this.IdEstadoCasillero = idEstadoCasillero;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Estado = estado;
        this.FechaCreacion = fechaCreacion;
        this.FechaModifica = fechaModifica;
    }

    public Integer getIdEstadoCasillero() { return IdEstadoCasillero; }
    public void setIdEstadoCasillero(Integer idEstadoCasillero) { this.IdEstadoCasillero = idEstadoCasillero; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getDescripcion() { return Descripcion; }
    public void setDescripcion(String descripcion) { Descripcion = descripcion; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModifica() { return FechaModifica; }
    public void setFechaModifica(String fechaModifica) { FechaModifica = fechaModifica; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdEstadoCasillero : " + getIdEstadoCasillero()
            + "\n Nombre            : " + getNombre()
            + "\n Descripcion       : " + getDescripcion()
            + "\n Estado            : " + getEstado()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModifica     : " + getFechaModifica()
            + "\n ----------------------------";
    }
}


