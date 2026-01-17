package BusinessLogic.Entities;

public class Casillero {


    private int intentos;
    private boolean bloqueado;
    private boolean abierto;
    private boolean solenoideActivo;

    public Casillero() {
        this.intentos = 0;
        this.bloqueado = false;
        this.abierto = false;
        this.solenoideActivo = false;
    }

    public boolean abrir() {
        if (bloqueado) {
            System.out.println("Casillero bloqueado");
            return false;
        }

        solenoideActivo = true;
        abierto = true;
        System.out.println("Casillero abierto");
        return true;
    }

    public void cerrar() {
        solenoideActivo = false;
        abierto = false;
        System.out.println("Casillero cerrado");
    }

    public boolean estadoSolenoide() {
        return solenoideActivo;
    }

    public void registrarIntentoFallido() {
        intentos++;
        if (intentos >= 3) {
            bloquear();
        }
    }

    public void bloquear() {
        bloqueado = true;
        solenoideActivo = false;
        abierto = false;
        System.out.println("Casillero bloqueado");
    }

    
    //public void desbloquear() {
    //    bloqueado = false;
    //    intentos = 0;
    //    System.out.println("Casillero desbloqueado");
    //}

    public boolean isBloqueado() {
        return bloqueado;
    }
}
