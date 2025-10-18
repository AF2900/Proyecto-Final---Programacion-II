package co.edu.uniquindio.pr2.proyectofinal.strategy;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import java.util.List;

public class ExportarCSV implements IExportarStrategy {
    @Override
    public void exportar(List<Envio> envios) {
        System.out.println("Simulando exportación a CSV...");
        for (Envio envio : envios) {
            System.out.println("CSV -> Código de envío: " + envio.getIdEnvio());
        }
    }
}