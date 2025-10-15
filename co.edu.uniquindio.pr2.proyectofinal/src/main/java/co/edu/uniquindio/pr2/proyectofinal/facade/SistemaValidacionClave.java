package co.edu.uniquindio.pr2.proyectofinal.facade;

public class SistemaValidacionClave {

    private static final String CLAVE_MAESTRA = "logisticaAJF";

    public boolean validarClave(String claveIngresada) {
        System.out.println("Verificando clave maestra...");
        return CLAVE_MAESTRA.equals(claveIngresada);
    }
}