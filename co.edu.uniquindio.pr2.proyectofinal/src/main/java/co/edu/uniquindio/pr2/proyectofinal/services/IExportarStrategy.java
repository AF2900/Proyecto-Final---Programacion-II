package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import java.util.List;

public interface IExportarStrategy {
    void exportar(List<Envio> envios);
}