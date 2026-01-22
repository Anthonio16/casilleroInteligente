package DataAccess.DTOs;

public class RegistroEventoDTO {

    private Integer IdRegistroEvento;
    private Integer IdTipoEvento;
    private Integer IdUsuario;
    private Integer IdCasillero;
    private String  FechaCreacion;
    private String  FechaModifica;


    public RegistroEventoDTO() {}

    public RegistroEventoDTO(Integer idTipoEvento, Integer idUsuario, Integer idCasillero) {
        IdRegistroEvento = 0;
        IdTipoEvento     = idTipoEvento;
        IdUsuario        = idUsuario;
        IdCasillero      = idCasillero;
    }

    public RegistroEventoDTO(Integer idRegistroEvento, Integer idTipoEvento, Integer idUsuario, Integer idCasillero,
                             String fechaCreacion, String fechaModifica) {
        IdRegistroEvento   = idRegistroEvento;
        IdTipoEvento       = idTipoEvento;
        IdUsuario          = idUsuario;
        IdCasillero        = idCasillero;
        FechaCreacion      = fechaCreacion;
        FechaModifica      = fechaModifica;
    }

    public Integer getIdRegistroEvento() { return IdRegistroEvento; }
    public void setIdRegistroEvento(Integer idRegistroEvento) { IdRegistroEvento = idRegistroEvento; }

    public Integer getIdTipoEvento() { return IdTipoEvento; }
    public void setIdTipoEvento(Integer idTipoEvento) { IdTipoEvento = idTipoEvento; }

    public Integer getIdUsuario() { return IdUsuario; }
    public void setIdUsuario(Integer idUsuario) { IdUsuario = idUsuario; }

    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer idCasillero) { IdCasillero = idCasillero; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModifica() { return FechaModifica; }
    public void setFechaModifica(String fechaModifica) { FechaModifica = fechaModifica; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\nIdRegistroEvento: " + getIdRegistroEvento()
            + "\nIdTipoEvento: " + getIdTipoEvento()
            + "\nIdUsuario: " + getIdUsuario()
            + "\nIdCasillero: " + getIdCasillero()
            + "\nFechaCreacion: " + getFechaCreacion()
            + "\nFechaModifica: " + getFechaModifica()
            + "\n ----------------------------";
    }
}