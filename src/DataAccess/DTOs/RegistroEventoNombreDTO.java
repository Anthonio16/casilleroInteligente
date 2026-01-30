package DataAccess.DTOs;

public class RegistroEventoNombreDTO {
    private Integer idRegistroEvento;
    private Integer idTipoEvento;
    private String  Nombre; // Nombre del TipoEvento
    private Integer idUsuario;
    private Integer idCasillero;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public RegistroEventoNombreDTO() {}

    
    
    public Integer getIdRegistroEvento() {
        return idRegistroEvento;
    }

    public void setIdRegistroEvento(Integer idRegistroEvento) {
        this.idRegistroEvento = idRegistroEvento;
    }

    public Integer getIdTipoEvento() {
        return idTipoEvento;
    }
    
    public void setIdTipoEvento(Integer idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }
    
    public String getNombre() {
        return Nombre;
    }
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCasillero() {
        return idCasillero;
    }
    
    public void setIdCasillero(Integer idCasillero) {
        this.idCasillero = idCasillero;
    }
    
    public String getEstado() {
        return Estado;
    }
    
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    
    public String getFechaCreacion() {
        return FechaCreacion;
    }
    
    public void setFechaCreacion(String FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }
    
    public String getFechaModificacion() {
        return FechaModificacion;
    }
    
    public void setFechaModificacion(String FechaModificacion) {
        this.FechaModificacion = FechaModificacion;
    }
    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento  : " + idRegistroEvento
            + "\n idTipoEvento      : " + idTipoEvento
            + "\n NombreTipoEvento  : " + Nombre
            + "\n idUsuario         : " + idUsuario
            + "\n idCasillero       : " + idCasillero
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModificacion : " + FechaModificacion
            + "\n ----------------------------";
    }
}

