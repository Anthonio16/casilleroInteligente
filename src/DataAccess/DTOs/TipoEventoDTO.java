package DataAccess.DTOs;

public class TipoEventoDTO {
    private Integer IdTipoEvento;
    private String  Nombre;
    private String  Descripcion;
    private String  FechaCreacion;
    private String  FechaModifica;

    public TipoEventoDTO() {
    }

    public TipoEventoDTO(String nombre, String descripcion) {
        IdTipoEvento = 0;
        Nombre = nombre;
        Descripcion = descripcion;
    }

    public TipoEventoDTO(Integer idTipoEvento, String nombre, String descripcion,
                          String fechaCreacion, String fechaModifica) {
        IdTipoEvento = idTipoEvento;
        Nombre = nombre;
        Descripcion = descripcion;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdTipoEvento() {
        return IdTipoEvento;
    }

    public void setIdTipoEvento(Integer idTipoEvento) {
        IdTipoEvento = idTipoEvento;
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
        + "\n IdTipoEvento: " + getIdTipoEvento()
        + "\n Nombre: "       + getNombre()
        + "\n Descripcion: "  + getDescripcion()
        + "\n FechaCreacion: " + getFechaCreacion()
        + "\n FechaModifica: " + getFechaModifica()
        + "\n ----------------------------";
    }
}