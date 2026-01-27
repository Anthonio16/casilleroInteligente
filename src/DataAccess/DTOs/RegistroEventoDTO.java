package DataAccess.DTOs;

public class RegistroEventoDTO {

    private Integer idRegistroEvento;
    private Integer idTipoEvento;
    private Integer idUsuario;
    private Integer idCasillero;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public RegistroEventoDTO() {}

    public RegistroEventoDTO(Integer idTipoEvento, Integer idUsuario, Integer idCasillero) {
        idRegistroEvento = 0;
        idTipoEvento = 0;
        idUsuario = 0;
        idCasillero = 0;
        Estado = "A";
    }

    public RegistroEventoDTO(Integer idRegistroEvento, Integer idTipoEvento, Integer idUsuario, Integer idCasillero,
                             String estado, String fechaCreacion, String fechaModificacion) {
        idRegistroEvento = 0;
        idTipoEvento = 0;
        idUsuario = 0;
        idCasillero = 0;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }

    public Integer getIdRegistroEvento() { return idRegistroEvento; }
    public void setIdRegistroEvento(Integer idRegistroEvento) { this.idRegistroEvento = idRegistroEvento; }

    public Integer getIdTipoEvento() { return idTipoEvento; }
    public void setIdTipoEvento(Integer idTipoEvento) { this.idTipoEvento = idTipoEvento; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento  : " + getIdRegistroEvento()
            + "\n idTipoEvento      : " + getIdTipoEvento()
            + "\n idUsuario         : " + getIdUsuario()
            + "\n idCasillero       : " + getIdCasillero()
            + "\n Estado            : " + getEstado()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModificacion : " + getFechaModificacion()
            + "\n ----------------------------";
    }
}
