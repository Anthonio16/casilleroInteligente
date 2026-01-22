package DataAccess.DTOs;

public class UsuarioDTO {

    private Integer IdUsuario;
    private Integer IdUsuarioTipo;
    private String  Nombre;
    private String  Clave;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, Integer idUsuarioTipo, String clave,
                      String descripcion, String estado) {
        IdUsuario     = 0;
        Nombre        = nombre;
        IdUsuarioTipo = idUsuarioTipo;
        Clave         = clave;
        Descripcion   = descripcion;
        Estado        = estado;
    }

    public UsuarioDTO(Integer idUsuario, String nombre, Integer idUsuarioTipo,
                      String clave, String descripcion, String estado,
                      String fechaCreacion, String fechaModifica) {
        IdUsuario     = idUsuario;
        Nombre        = nombre;
        IdUsuarioTipo = idUsuarioTipo;
        Clave         = clave;
        Descripcion   = descripcion;
        Estado        = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdUsuario() { return IdUsuario; }
    public void setIdUsuario(Integer idUsuario) { IdUsuario = idUsuario; }

    public Integer getIdUsuarioTipo() { return IdUsuarioTipo; }
    public void setIdUsuarioTipo(Integer idUsuarioTipo) { IdUsuarioTipo = idUsuarioTipo; }

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

    public String getFechaModifica() { return FechaModifica; }
    public void setFechaModifica(String fechaModifica) { FechaModifica = fechaModifica; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\nIdUsuario: " + getIdUsuario()
            + "\nNombre: " + getNombre()
            + "\nIdUsuarioTipo: " + getIdUsuarioTipo()
            + "\nClave: " + getClave()
            + "\nDescripcion: " + getDescripcion()
            + "\nEstado: " + getEstado()
            + "\nFechaCreacion: " + getFechaCreacion()
            + "\nFechaModifica: " + getFechaModifica()
            + "\n----------------------------";
    }
}

