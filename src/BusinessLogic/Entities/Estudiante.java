package BusinessLogic.Entities;

public class Estudiante extends Usuario {

    public Estudiante(Integer idUsuario, String nombre) {
        super(idUsuario, 2, nombre, EstadoRegistro.A);
    }

    public boolean cambiarPinTemporal(int pinActual, int nuevoPin) {
        if (pinTemporal == null) return false;

        if (pinTemporal == pinActual) {
            setPinTemporal(nuevoPin);
            return true;
        }
        intentosPin++;
        return false;
    }
}

