package BusinessLogic.Entities;

public class Indicadores {

    private boolean ledRojo;
    private boolean ledVerde;

    public void ok() {
        ledVerde = true;
        ledRojo = false;
    }

    public void error() {
        ledRojo = true;
        ledVerde = false;
    }

    public void apagar() {
        ledRojo = false;
        ledVerde = false;
    }

    public boolean isLedRojo() { return ledRojo; }
    public boolean isLedVerde() { return ledVerde; }
}

