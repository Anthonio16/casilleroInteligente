package BusinessLogic.Entities;

public class Indicadores {

    private boolean ledRojo;
    private boolean ledVerde;

    public void encenderLedRojo() {
        ledRojo = true;
    }

    public void encenderLedVerde() {
        ledVerde = true;
    }

    public void parpadearError() {
        // Lógica para parpadear el LED rojo
    }

    
}
