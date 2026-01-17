package UserInterface;

import BusinessLogic.Entities.*;

import java.util.Scanner;

public class ConsolaUI {

    private Scanner sc = new Scanner(System.in);
    public void iniciar() {
    
        Casillero casillero = new Casillero();
        Estudiante estudiante = new Estudiante("pepe", 1234);
        Indicadores leds = new Indicadores();
    
        System.out.println("Casillero Inteligente Iniciado");
    
        while (estudiante.getIntentos() < 3) {
    
            System.out.print("Ingrese el Pin: ");
            int pinIngresado = sc.nextInt();
    
            if (estudiante.validarPin(pinIngresado)) {
                leds.encenderLedVerde();
                System.out.println("PIN correcto. Casillero abierto.");
                casillero.abrir();
                return; 
            } else {
                leds.encenderLedRojo();
                System.out.println("PIN incorrecto. Intentos fallidos: "
                        + estudiante.getIntentos());
            }
        }
    
        leds.parpadearError();
        System.out.println("Casillero bloqueado por demasiados intentos.");
    }

}
//    public static void iniciar() {
//
//        Scanner sc = new Scanner(System.in);
//
//        Casillero casillero = new Casillero();
//        Estudiante estudiante = new Estudiante("pepe", 1234, casillero);
//
//        while (true) {
//            System.out.println("\nIngrese PIN:");
//            int pin = sc.nextInt();
//
//            boolean ok = estudiante.abrirCasillero(pin);
//
//            if (ok) {
//                System.out.println("Presione 1 para cerrar casillero");
//                int op = sc.nextInt();
//                if (op == 1) {
//                    estudiante.cerrarCasillero();
//                }
//            } else if (casillero.isBloqueado()) {
//                System.out.println("Casillero bloqueado. Contacte al administrador.");
//                break;
//            }
//        }
//    }
