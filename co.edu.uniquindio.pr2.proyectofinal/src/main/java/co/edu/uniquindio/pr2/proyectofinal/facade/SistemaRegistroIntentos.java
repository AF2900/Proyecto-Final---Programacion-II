package co.edu.uniquindio.pr2.proyectofinal.facade;

public class SistemaRegistroIntentos {

    private int intentos = 0;

    public void registrarIntento() {
        intentos++;
        System.out.println("Intento #" + intentos + " registrado.");
    }

    public void reiniciarIntentos() {
        intentos = 0;
        System.out.println("Intentos reiniciados.");
    }

    public int getIntentos() {
        return intentos;
    }
}