package DataAccess.DTOs;

public class CredencialCasilleroDTO {

    private Integer idCredencialCasillero;
    private Integer idCasillero;
    private String  pinHash;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModificacion;

    public CredencialCasilleroDTO() {}

    public CredencialCasilleroDTO(Integer idCasillero, String pinHash) {
        this.idCredencialCasillero = 0;
        this.idCasillero = idCasillero;
        this.pinHash = pinHash;
        this.Estado = "A";
    }

    public CredencialCasilleroDTO(Integer idCredencialCasillero, Integer idCasillero, String pinHash,
                                  String estado, String fechaCreacion, String fechaModificacion) {
        this.idCredencialCasillero = idCredencialCasillero;
        this.idCasillero = idCasillero;
        this.pinHash = pinHash;
        this.Estado = estado;
        this.FechaCreacion = fechaCreacion;
        this.FechaModificacion = fechaModificacion;
    }

    public Integer getIdCredencialCasillero() { return idCredencialCasillero; }
    public void setIdCredencialCasillero(Integer idCredencialCasillero) { this.idCredencialCasillero = idCredencialCasillero; }

    public Integer getIdCasillero() { return idCasillero; }
    public void setIdCasillero(Integer idCasillero) { this.idCasillero = idCasillero; }

    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { FechaCreacion = fechaCreacion; }

    public String getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n idCredencialCasillero : " + idCredencialCasillero
            + "\n idCasillero           : " + idCasillero
            + "\n pinHash               : " + pinHash
            + "\n Estado                : " + Estado
            + "\n FechaCreacion         : " + FechaCreacion
            + "\n FechaModificacion     : " + FechaModificacion
            + "\n ----------------------------";
    }
}


