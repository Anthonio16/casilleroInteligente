package DataAccess.DTOs;

public class CasilleroDTO {

    private Integer idCasillero;
    private Integer idEstadoCasillero;
    private Integer idEstudiante;
    private Integer IntentosFallidos;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public CasilleroDTO() {}

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public Integer getIdEstadoCasillero() { return idEstadoCasillero; }
    public void setIdEstadoCasillero(Integer idEstadoCasillero) { this.idEstadoCasillero = idEstadoCasillero; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

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
            + "\n idCasillero       : " + idCasillero
            + "\n idEstudiante      : " + idEstudiante
            + "\n idEstadoCasillero : " + idEstadoCasillero
            + "\n IntentosFallidos  : " + IntentosFallidos
            + "\n Descripcion       : " + Descripcion
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }
}




