package BusinessLogic.Entities;

public abstract class Usuario {

    protected Integer idUsuario;
    protected Integer idUsuarioTipo;  // 1=Admin, 2=Estudiante (según inserts)
    protected String nombre;
    protected EstadoRegistro estado;

    // Solo para pruebas locales (en real: PIN HASH en BD)
    protected Integer pinTemporal;
    protected int intentosPin;

    protected Usuario(Integer idUsuario, Integer idUsuarioTipo, String nombre, EstadoRegistro estado) {
        this.idUsuario = idUsuario;
        this.idUsuarioTipo = idUsuarioTipo;
        this.nombre = nombre;
        this.estado = estado == null ? EstadoRegistro.A : estado;
        this.intentosPin = 0;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public Integer getIdUsuarioTipo() { return idUsuarioTipo; }
    public String getNombre() { return nombre; }
    public EstadoRegistro getEstado() { return estado; }

    public void setPinTemporal(Integer pinTemporal) {
        this.pinTemporal = pinTemporal;
        this.intentosPin = 0;
    }

    public int getIntentosPin() { return intentosPin; }
    public void resetIntentosPin() { intentosPin = 0; }

    // Para prototipo: valida vs pinTemporal
    public boolean validarPinTemporal(int pinIngresado) {
        if (pinTemporal == null) return false;

        if (pinTemporal == pinIngresado) {
            intentosPin = 0;
            return true;
        }
        intentosPin++;
        return false;
    }
}
