package DataAccess.DTOs;

public class UsuarioTipoDTO {
    
    private Integer idUsuarioTipo;
    private String  Nombre;
    private String  Descripcion;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;


    
    public UsuarioTipoDTO() {
    }
    public UsuarioTipoDTO(String nombre, String estado) {
        Nombre = nombre;
        Estado = estado;
    }
    public UsuarioTipoDTO(Integer idUsuarioTipo, String nombre, String descripcion, String estado, String fechaCreacion,
            String fechaModifica) {
        this.idUsuarioTipo = idUsuarioTipo;
        Nombre = nombre;
        Descripcion = descripcion;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }
    public Integer getIdUsuarioTipo() {
        return idUsuarioTipo;
    }
    public void setIdUsuarioTipo(Integer idUsuarioTipo) {
        this.idUsuarioTipo = idUsuarioTipo;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
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
            + "\nIdUsuarioTipo: " +     getIdUsuarioTipo()
            + "\nNombre: " +            getNombre()
            + "\nDescripcion: " +       getDescripcion()
            + "\nEstado: " +            getEstado()
            + "\nFechaCreacion: " +     getFechaCreacion()
            + "\nFechaModifica: " +     getFechaModifica()
            + "\n----------------------------";
    }



}
