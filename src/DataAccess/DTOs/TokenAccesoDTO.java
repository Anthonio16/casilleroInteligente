package DataAccess.DTOs;

public class TokenAccesoDTO {
    private Integer idTokenacceso;
    private Integer idSolicitud;
    private Integer idCasillero;
    private String  TokenHash;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;
    private String  FechaExpiracion;

    public TokenAccesoDTO() {}

    public TokenAccesoDTO(Integer idSolicitud, Integer idCasillero, String tokenHash, String fechaExpiracion) {
        this.idTokenacceso = 0;
        this.idSolicitud = idSolicitud;
        this.idCasillero = idCasillero;
        this.TokenHash = tokenHash;
        this.Estado = "A";
        this.FechaExpiracion = fechaExpiracion;
    }

    public Integer getIdTokenacceso() { return idTokenacceso; }
    public void setIdTokenacceso(Integer idTokenacceso) { this.idTokenacceso = idTokenacceso; }

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public String getTokenHash() { return TokenHash; }
    public void setTokenHash(String tokenHash) { TokenHash = tokenHash; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    public String getFechaExpiracion() { return FechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { FechaExpiracion = fechaExpiracion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idTokenacceso      : " + getIdTokenacceso()
            + "\n idSolicitud        : " + getIdSolicitud()
            + "\n idCasillero        : " + getIdCasillero()
            + "\n TokenHash          : " + getTokenHash()
            + "\n Estado             : " + getEstado()
            + "\n FechaCreacion      : " + getFechaCreacion()
            + "\n FechaModificacion  : " + getFechaModificacion()
            + "\n FechaExpiracion    : " + getFechaExpiracion()
            + "\n ----------------------------";
    }
}


