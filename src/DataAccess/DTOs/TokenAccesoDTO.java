package DataAccess.DTOs;

public class TokenAccesoDTO {
    private Integer IdTokenacceso;
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private String  TokenHash;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;
    private String  FechaExpiracion;

    
    
    public TokenAccesoDTO() {}
    
    public TokenAccesoDTO(String tokenHash, String estado, String fechaCreacion) {
        TokenHash = tokenHash;
        Estado = estado;
        FechaCreacion = fechaCreacion;
    }
    public TokenAccesoDTO(Integer IdTokenacceso, Integer IdSolicitud, Integer IdCasillero, String tokenHash,
            String estado, String fechaCreacion, String fechaModificacion, String fechaExpiracion) {
        IdTokenacceso = 0;
        IdSolicitud = 0;
        IdCasillero = 0;
        TokenHash = tokenHash;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
        FechaExpiracion = fechaExpiracion;
    }
    public Integer getIdTokenacceso() { return IdTokenacceso; }
    public void setIdTokenacceso(Integer IdTokenacceso) { this.IdTokenacceso = IdTokenacceso; }
    
    public Integer getIdSolicitud() { return IdSolicitud; }
    public void setIdSolicitud(Integer IdSolicitud) { this.IdSolicitud = IdSolicitud; }
    
    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer IdCasillero) { this.IdCasillero = IdCasillero; }

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
            + "\n IdTokenacceso      : " + IdTokenacceso
            + "\n IdSolicitud        : " + IdSolicitud
            + "\n IdCasillero        : " + IdCasillero
            + "\n TokenHash          : " + TokenHash
            + "\n Estado             : " + Estado
            + "\n FechaCreacion      : " + FechaCreacion
            + "\n FechaModificacion  : " + FechaModificacion
            + "\n FechaExpiracion    : " + FechaExpiracion
            + "\n ----------------------------";
    }
}
