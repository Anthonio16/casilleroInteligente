package DataAccess.DTOs;

public class UsuarioDTO {
    private Integer idUsuario;
    private Integer idUsuarioTipo;
    private String  Nombre;
    private String  Clave;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public UsuarioDTO() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdUsuarioTipo() { return idUsuarioTipo; }
    public void setIdUsuarioTipo(Integer idUsuarioTipo) { this.idUsuarioTipo = idUsuarioTipo; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getClave() { return Clave; }
    public void setClave(String clave) { Clave = clave; }

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
            + "\n idUsuario         : " + idUsuario
            + "\n idUsuarioTipo     : " + idUsuarioTipo
            + "\n Nombre            : " + Nombre
            + "\n Clave             : " + Clave
            + "\n Descripcion       : " + Descripcion
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }
}


