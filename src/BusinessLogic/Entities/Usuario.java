package BusinessLogic.Entities;

public abstract class Usuario {

    protected String usuario;
    protected int pin;
    private int intentos;

    public Usuario(String usuario, int pin) {
        this.usuario = usuario;
        this.pin = pin;
        this.intentos = 0;
    }

    public boolean validarPin(int pinIngresado) {

        if (this.pin == pinIngresado) {
            intentos = 0;
            return true;
        } else {
            intentos++;
            return false;
        }
    }

    public int getIntentos() {
        return intentos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void registrarPin(int nuevoPin) {
        this.pin = nuevoPin;
    }
}
