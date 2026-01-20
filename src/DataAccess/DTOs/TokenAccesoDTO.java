package DataAccess.DTOs;

public class TokenAccesoDTO {
    
    private Integer IdTokenAcceso;
    private Integer IdSolicitud;
    private Integer IdCasillero;
    private String TokenHash;
    private String Estado;
    private String FechaCreacion;
    private String FechaExpiracion;
    
    public TokenAccesoDTO() {}
    
    public TokenAccesoDTO(Integer IdTokenAcceso, Integer IdSolicitud, Integer IdCasillero, String tokenHash,
        String estado, String fechaCreacion, String fechaExpiracion) {
            this.IdTokenAcceso = IdTokenAcceso;
        this.IdSolicitud = IdSolicitud;
        this.IdCasillero = IdCasillero;
        TokenHash = tokenHash;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaExpiracion = fechaExpiracion;
    }
    
    public Integer getIdTokenAcceso() {
        return IdTokenAcceso;
    }

    public void setIdTokenAcceso(Integer IdTokenAcceso) {
        this.IdTokenAcceso = IdTokenAcceso;
    }

    public Integer getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(Integer IdSolicitud) {
        this.IdSolicitud = IdSolicitud;
    }

    public Integer getIdCasillero() {
        return IdCasillero;
    }

    public void setIdCasillero(Integer IdCasillero) {
        this.IdCasillero = IdCasillero;
    }

    public String getTokenHash() {
        return TokenHash;
    }

    public void setTokenHash(String tokenHash) {
        TokenHash = tokenHash;
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

    public String getFechaExpiracion() {
        return FechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        FechaExpiracion = fechaExpiracion;
    }
    
    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdTokenAcceso     : "+ getIdTokenAcceso       ()
        + "\n IdSolicitud       : "+ getIdSolicitud         ()
        + "\n IdCasillero       : "+ getIdCasillero         ()
        + "\n TokenHash         : "+ getTokenHash           ()
        + "\n Estado            : "+ getEstado              ()  
        + "\n FechaCreacion     : "+ getFechaCreacion       ()
        + "\n FechaExpiracion   : "+ getFechaExpiracion     ();
    }    
}   
