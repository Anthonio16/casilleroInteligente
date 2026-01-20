package DataAccess.DTOs;

public class EstadoCasilleroDTO {
    private Integer IdEstadoCasillero;
    private String nombre;
    private String descripcion;
    private String Estado;
    private String FechaCreacion;
    private String FechaModificacion;

    
    public EstadoCasilleroDTO() {}
    
    public EstadoCasilleroDTO(Integer IdEstadoCasillero, String nombre, String descripcion, String estado,
        String fechaCreacion, String fechaModificacion) {
            this.IdEstadoCasillero = IdEstadoCasillero;
            this.nombre = nombre;
            this.descripcion = descripcion;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }
    
    
    public Integer getIdEstadoCasillero() {
        return IdEstadoCasillero;
    }

    public void setIdEstadoCasillero(Integer IdEstadoCasillero) {
        this.IdEstadoCasillero = IdEstadoCasillero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        FechaModificacion = fechaModificacion;
    }
    
    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdEstadoCasillero:" + getIdEstadoCasillero()
        + "\n nombre           :" + getNombre           () 
        + "\n descripcion      :" + getDescripcion      () 
        + "\n Estado           :" + getEstado           () 
        + "\n FechaCreacion    :" + getFechaCreacion    () 
        + "\n FechaModificacion:" + getFechaModificacion();
    }
}
