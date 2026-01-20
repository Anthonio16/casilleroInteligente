package DataAccess.DTOs;

public class CasilleroDTO {
    private Integer IdCasillero;
    private Integer IdEstudiante;
    private Integer IdEstadoCasillero;
    private Integer IntentosFallidos;
    private String Descripcion;
    private String Estado;
    private String FechaCreacion;
    private String FechaModifica;
    
    
    public CasilleroDTO() {}
    
    public CasilleroDTO(Integer IdCasillero, Integer IdEstudiante, Integer IdEstadoCasillero, Integer IntentosFallidos,
        String descripcion, String estado, String fechaCreacion, String fechaModificacion) {
        this.IdCasillero = IdCasillero;
        this.IdEstudiante = IdEstudiante;
        this.IdEstadoCasillero = IdEstadoCasillero;
        this.IntentosFallidos = IntentosFallidos;
        Descripcion = descripcion;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModificacion;
    }
    
    
    public Integer getIdCasillero() {
        return IdCasillero;
    }

    public void setIdCasillero(Integer IdCasillero) {
        this.IdCasillero = IdCasillero;
    }

    public Integer getIdEstudiante() {
        return IdEstudiante;
    }

    public void setIdEstudiante(Integer IdEstudiante) {
        this.IdEstudiante = IdEstudiante;
    }

    public Integer getIdEstadoCasillero() {
        return IdEstadoCasillero;
    }

    public void setIdEstadoCasillero(Integer IdEstadoCasillero) {
        this.IdEstadoCasillero = IdEstadoCasillero;
    }

    public Integer getIntentosFallidos() {
        return IntentosFallidos;
    }

    public void setIntentosFallidos(Integer IntentosFallidos) {
        this.IntentosFallidos = IntentosFallidos;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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

    public String getFechaModifica() {
        return FechaModifica;
    }

    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdCasillero       :" + getIdCasillero      ()
        + "\n IdEstudiante      :" + getIdEstudiante     ()
        + "\n IdEstadoCasillero :" + getIdEstadoCasillero()
        + "\n IntentosFallidos  :" + getIntentosFallidos ()
        + "\n Descripcion       :" + getDescripcion      ()
        + "\n Estado            :" + getEstado           ()
        + "\n FechaCreacion     :" + getFechaCreacion    ()
        + "\n FechaModifica     :" + getFechaModifica    ();
    }
}
