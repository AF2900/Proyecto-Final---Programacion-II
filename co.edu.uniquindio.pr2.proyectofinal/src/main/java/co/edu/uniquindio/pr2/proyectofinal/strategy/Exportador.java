package co.edu.uniquindio.pr2.proyectofinal.strategy;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import java.util.List;

public class Exportador {
    private IExportarStrategy estrategia;

    public void setEstrategia(IExportarStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void ejecutarExportacion(List<Envio> envios) {
        if (estrategia != null) {
            estrategia.exportar(envios);
        } else {
            System.out.println("No hay exportación seleccionada.");
        }
    }
}