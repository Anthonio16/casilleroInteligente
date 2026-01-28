package DataAccess.DTOs;

public class CasilleroDTO {

    private Integer IdCasillero;
    private Integer IdEstudiante;
    private Integer IdEstadoCasillero;
    private Integer IntentosFallidos;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public CasilleroDTO() {}

    public Integer getIdCasillero() { return IdCasillero; }

    public void setIdCasillero(Integer IdCasillero) { this.IdCasillero = IdCasillero; }
    
    public Integer getIdEstudiante() { return IdEstudiante; }
    
    public Integer getIdEstadoCasillero() { return IdEstadoCasillero; }

    public void setIdEstadoCasillero(Integer IdEstadoCasillero) { this.IdEstadoCasillero = IdEstadoCasillero; }

    public void setIdEstudiante(Integer IdEstudiante) { this.IdEstudiante = IdEstudiante; }

    public Integer getIntentosFallidos() { return IntentosFallidos; }
    public void setIntentosFallidos(Integer intentosFallidos) { IntentosFallidos = intentosFallidos; }

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
            + "\n IdCasillero       : " + IdCasillero
            + "\n IdEstudiante      : " + IdEstudiante
            + "\n IdEstadoCasillero : " + IdEstadoCasillero
            + "\n IntentosFallidos  : " + IntentosFallidos
            + "\n Descripcion       : " + Descripcion
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }
}




