package BusinessLogic.Entities;

public class Casillero {
    private final int idCasillero;
    private Integer idEstudiante;          
    private EstadoCasillero estadoCasillero;
    private int intentosFallidos;
    private String estadoRegistro;         // 'A' o 'X' 

    public Casillero(int idCasillero, Integer idEstudiante, EstadoCasillero estadoCasillero,
                     int intentosFallidos, String estadoRegistro) {
        this.idCasillero = idCasillero;
        this.idEstudiante = idEstudiante;
        this.estadoCasillero = estadoCasillero;
        this.intentosFallidos = intentosFallidos;
        this.estadoRegistro = estadoRegistro;
    }

    public int getIdCasillero() { return idCasillero; }
    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public EstadoCasillero getEstadoCasillero() { return estadoCasillero; }
    public void setEstadoCasillero(EstadoCasillero estadoCasillero) { this.estadoCasillero = estadoCasillero; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public String getEstadoRegistro() { return estadoRegistro; }
    public void setEstadoRegistro(String estadoRegistro) { this.estadoRegistro = estadoRegistro; }

    public boolean estaBloqueado() {
        return estadoCasillero == EstadoCasillero.LOCKED;
    }
}


