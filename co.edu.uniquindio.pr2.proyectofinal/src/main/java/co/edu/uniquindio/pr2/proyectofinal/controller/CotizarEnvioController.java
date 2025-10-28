package co.edu.uniquindio.pr2.proyectofinal.controller;

public class CotizarEnvioController {

    public double calcularCostoEnvio(String origen, String destino, double peso, double volumen, String prioridad, boolean seguro, boolean fragil, boolean firma) {
        double costoBase = (peso * 0.5) + (volumen * 0.02);
        double costo;

        switch (prioridad) {
            case "Alta" -> costo = costoBase * 1.4;
            case "Urgente" -> costo = costoBase * 1.8;
            default -> costo = costoBase;
        }

        if (seguro) costo += 10000;
        if (fragil) costo += 5000;
        if (firma) costo += 3000;

        return costo;
    }
}