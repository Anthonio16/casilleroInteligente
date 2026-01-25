package DataAccess.DTOs;

public class UsuarioDTO {
    private Integer idUsuario;
    private Integer idUsuarioTipo;
    private String  nombre;
    private String  clave;
    private String  descripcion;
    private String  estado;
    private String  fechaCreacion;
    private String  fechaModificacion;

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Integer getIdUsuarioTipo() {
        return idUsuarioTipo;
    }
    public void setIdUsuarioTipo(Integer idUsuarioTipo) {
        this.idUsuarioTipo = idUsuarioTipo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getFechaModificacion() {
        return fechaModificacion;
    }
    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    @Override
    public String toString() {
        return getClass().getName()
            + "\nIdUsuario: " +         getIdUsuario()
            + "\nIdUsuarioTipo: " +     getIdUsuarioTipo()
            + "\nNombre: " +            getNombre()
            + "\nClave: " +             getClave()
            + "\nDescripcion: " +       getDescripcion()
            + "\nEstado: " +            getEstado()
            + "\nFechaCreacion: " +     getFechaCreacion()
            + "\nFechaModificacion: " + getFechaModificacion()
            + "\n----------------------------";
    }

}

