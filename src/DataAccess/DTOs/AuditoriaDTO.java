package DataAccess.DTOs;

public class AuditoriaDTO {
    
    private Integer idRegistroEvento;
    private String  Fecha;
    private Integer idCasillero;
    private String  CasilleroDescripcion;
    private String  EstadoCasillero;
    private Integer idEstudiante;
    private Integer idUsuario;
    private String  UsuarioNombre;
    private String  UsuarioRol;
    private String  EventoNombre;
    private String  Resultado;

    public AuditoriaDTO() {}

    public Integer getIdRegistroEvento() { return idRegistroEvento; }
    public void setIdRegistroEvento(Integer idRegistroEvento) { this.idRegistroEvento = idRegistroEvento; }

    public String getFecha() { return Fecha; }
    public void setFecha(String Fecha) { this.Fecha = Fecha; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public String getCasilleroDescripcion() { return CasilleroDescripcion; }
    public void setCasilleroDescripcion(String CasilleroDescripcion) { this.CasilleroDescripcion = CasilleroDescripcion; }

    public String getEstadoCasillero() { return EstadoCasillero; }
    public void setEstadoCasillero(String EstadoCasillero) { this.EstadoCasillero = EstadoCasillero; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsuarioNombre() { return UsuarioNombre; }
    public void setUsuarioNombre(String UsuarioNombre) { this.UsuarioNombre = UsuarioNombre; }

    public String getUsuarioRol() { return UsuarioRol; }
    public void setUsuarioRol(String UsuarioRol) { this.UsuarioRol = UsuarioRol; }

    public String getEventoNombre() { return EventoNombre; }
    public void setEventoNombre(String EventoNombre) { this.EventoNombre = EventoNombre; }

    public String getResultado() { return Resultado; }
    public void setResultado(String Resultado) { this.Resultado = Resultado; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idRegistroEvento  : " + idRegistroEvento
            + "\n Fecha             : " + Fecha
            + "\n CasilleroDescripcion: " + CasilleroDescripcion
            + "\n EstadoCasillero   : "  + EstadoCasillero
            + "\n idUsuario         : "  + idUsuario
            + "\n UsuarioNombre     : "  + UsuarioNombre
            + "\n UsuarioRol        : "  + UsuarioRol
            + "\n idCasillero       : " + idCasillero
            + "\n idEstudiante      : " + idEstudiante
            + "\n EventoNombre      : "  + EventoNombre
            + "\n Resultado         : "  + Resultado

            + "\n ----------------------------";
    }
}

