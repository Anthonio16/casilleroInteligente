package DataAccess.DTOs;

public class CredencialCasilleroDTO {

    private Integer IdCredencialCasillero;
    private Integer IdCasillero;
    private String  pinHash;

    public CredencialCasilleroDTO() {}

    public CredencialCasilleroDTO(Integer idCasillero, String pinHash) {
        IdCredencialCasillero = 0;
        IdCasillero = idCasillero;
        this.pinHash = pinHash;
    }

    public CredencialCasilleroDTO(Integer idCredencialCasillero, Integer idCasillero, String pinHash) {
        IdCredencialCasillero = idCredencialCasillero;
        IdCasillero = idCasillero;
        this.pinHash = pinHash;
    }

    public Integer getIdCredencialCasillero() { return IdCredencialCasillero; }
    public void setIdCredencialCasillero(Integer idCredencialCasillero) { IdCredencialCasillero = idCredencialCasillero; }

    public Integer getIdCasillero() { return IdCasillero; }
    public void setIdCasillero(Integer idCasillero) { IdCasillero = idCasillero; }

    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }

    @Override
    public String toString() {
        return getClass().getName()
            + "\nIdCredencialCasillero: " + getIdCredencialCasillero()
            + "\nIdCasillero: " + getIdCasillero()
            + "\npinHash: " + getPinHash()
            + "\n----------------------------";
    }
}
