package BusinessLogic.Entities;

public class Administrador extends Usuario {

    private boolean modoAdminActivo;

    public Administrador(Integer idUsuario, String nombre) {
        super(idUsuario, 1, nombre, EstadoRegistro.A);
        this.modoAdminActivo = false;
    }

    public boolean ingresarModoAdmin(int pinIngresado) {
        boolean ok = validarPinTemporal(pinIngresado);
        modoAdminActivo = ok;
        return ok;
    }

    public void salirModoAdmin() {
        modoAdminActivo = false;
    }

    public boolean isModoAdminActivo() {
        return modoAdminActivo;
    }

    public boolean puedeAprobarSolicitudes() {
        return modoAdminActivo;
    }
}
