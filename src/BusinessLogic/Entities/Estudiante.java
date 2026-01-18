package BusinessLogic.Entities;
import BusinessLogic.Interfaces.IAutenticacion;

public class Estudiante extends Usuario implements IAutenticacion {

    private int pinActual;
    

    public Estudiante(String usiario, int pin) {
        super(usiario, pin);
        this.pinActual = pin;
    }

    public void leerEntrada(){
    }

    public boolean cambiarPIN(int pinActual) {
        if (this.pin == pinActual) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validarPin(int pinIngresado){
        if (super.validarPin(pinIngresado)) {
            return true;
        } else {
            return false;
        }
    }
}
