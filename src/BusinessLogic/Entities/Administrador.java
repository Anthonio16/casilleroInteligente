package BusinessLogic.Entities;

import BusinessLogic.Interfaces.IAutenticacion;

public class Administrador extends Usuario implements IAutenticacion{
    
    private int pinMaster;
    private int intentosAdmin;
    private boolean modoAdminActivo;

    public Administrador(String usuario, int pinMaster) {
        super(usuario, pinMaster);
        this.pinMaster = pinMaster;
        this.intentosAdmin = 0;
        this.modoAdminActivo = false;
    }

    public void ingrModoAdmin(int pinMaster){
        if(this.pinMaster == pinMaster){
            modoAdminActivo = true;
            intentosAdmin = 0;
        } else {
            intentosAdmin++;
        }
    }

    public void salirModoAdmin(){
        modoAdminActivo = false;
    }

    public boolean restablecerPinUsuario(){
        return modoAdminActivo;
    }
    
    @Override
    public boolean validarPin(int pin) {
        if (pin == pinMaster) {
            modoAdminActivo = true;
            return true;
        }
        intentosAdmin++;
        return false;
    }

    public boolean isModoAdminActivo() {
        return modoAdminActivo;
    }

}
