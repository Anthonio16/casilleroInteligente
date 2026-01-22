package DataAccess.DTOs;

public class TokenAccesoDTO {

    private Integer IdTokenAcceso;
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private String  TokenHash;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;
    private String  FechaExpiracion;

    public TokenAccesoDTO() {}

    // Constructor simple (cuando creas un token nuevo)
    public TokenAccesoDTO(Integer idSolicitud, Integer idCasillero, String tokenHash, String estado, String fechaExpiracion) {
        IdTokenAcceso   = 0;
        IdSolicitud     = idSolicitud;
        IdCasillero     = idCasillero;
        TokenHash       = tokenHash;
        Estado          = estado;
        FechaExpiracion = fechaExpiracion;
    }

    // Constructor completo (cuando lees de la BD)
    public TokenAccesoDTO(Integer idTokenAcceso, Integer idSolicitud, Integer idCasillero, String tokenHash,
                          String estado, String fechaCreacion, String fechaModifica, String fechaExpiracion) {
        IdTokenAcceso   = idTokenAcceso;
        IdSolicitud     = idSolicitud;
        IdCasillero     = idCasillero;
        TokenHash       = tokenHash;
        Estado          = estado;
        FechaCreacion   = fechaCreacion;
        FechaModifica   = fechaModifica;
        FechaExpiracion = fechaExpiracion;
    }

    public Integer getIdTokenAcceso() { return IdTokenAcceso; }
    public void setIdTokenAcceso(Integer idTokenAcceso) { IdTokenAcceso = idTokenAcceso; }

    public Integer getIdSolicitud() { return IdSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { IdSolicitud = idSolicitud; }

    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer idCasillero) { IdCasillero = idCasillero; }

    public String getTokenHash() { return TokenHash; }
    public void setTokenHash(String tokenHash) { TokenHash = tokenHash; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModifica() { return FechaModifica; }
    public void setFechaModifica(String fechaModifica) { FechaModifica = fechaModifica; }

    public String getFechaExpiracion() { return FechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { FechaExpiracion = fechaExpiracion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdTokenAcceso     : " + getIdTokenAcceso()
            + "\n IdSolicitud       : " + getIdSolicitud()
            + "\n IdCasillero       : " + getIdCasillero()
            + "\n TokenHash         : " + getTokenHash()
            + "\n Estado            : " + getEstado()
            + "\n FechaCreacion     : " + getFechaCreacion()
            + "\n FechaModifica     : " + getFechaModifica()
            + "\n FechaExpiracion   : " + getFechaExpiracion()
            + "\n ----------------------------";
    }
}

