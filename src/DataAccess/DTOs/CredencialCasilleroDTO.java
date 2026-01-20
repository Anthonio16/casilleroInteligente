package DataAccess.DTOs;

public class CredencialCasilleroDTO {

    private Integer IdCredencialCasillero;
    private Integer IdCasillero;
    private String  pinHash;
    private String  FechaCreacion;
    private String  FechaModifica;
    
    public CredencialCasilleroDTO() {
    }

    public CredencialCasilleroDTO(Integer idCasillero, String pinHash) {
        IdCredencialCasillero = 0;
        IdCasillero          = idCasillero;
        this.pinHash         = pinHash;
    }

    public CredencialCasilleroDTO(Integer idCredencialCasillero, Integer idCasillero,
                                 String pinHash, String fechaCreacion, String fechaModifica) {
        IdCredencialCasillero = idCredencialCasillero;
        IdCasillero          = idCasillero;
        this.pinHash         = pinHash;
        FechaCreacion        = fechaCreacion;
        FechaModifica        = fechaModifica;
    }

    public Integer getIdCredencialCasillero() {
        return IdCredencialCasillero;
    }
    public void setIdCredencialCasillero(Integer idCredencialCasillero) {
        IdCredencialCasillero = idCredencialCasillero;
    }

    public Integer getIdCasillero() {
        return IdCasillero;
    }

    public void setIdCasillero(Integer idCasillero) {
        IdCasillero = idCasillero;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaModifica() {
        return FechaModifica;
    }

    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }

    @Override

    public String toString() {
        return getClass().getName()
        + "\n IdCredencialCasillero: " + getIdCredencialCasillero()
        + "\n IdCasillero: "          + getIdCasillero()
        + "\n pinHash: "              + getPinHash()
        + "\n FechaCreacion: "        + getFechaCreacion()
        + "\n FechaModifica: "        + getFechaModifica()
        + "\n ----------------------------";
    }

}