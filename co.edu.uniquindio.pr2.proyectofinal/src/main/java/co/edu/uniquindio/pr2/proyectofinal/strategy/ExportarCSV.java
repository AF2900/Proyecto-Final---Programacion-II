package co.edu.uniquindio.pr2.proyectofinal.strategy;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ExportarCSV implements IExportarStrategy {

    @Override
    public void exportar(List<Envio> envios) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Historial como CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"));
        fileChooser.setInitialFileName("HistorialEnvios.csv");

        File archivoSeleccionado = fileChooser.showSaveDialog(new Stage());
        if (archivoSeleccionado == null) return;

        try (FileWriter writer = new FileWriter(archivoSeleccionado)) {
            writer.append("Código,Estado,Fecha Creación,Fecha Entrega,Costo,Incidencias,Servicios Adicionales\n");

            for (Envio envio : envios) {
                String incidencias = envio.getListaIncidencias().isEmpty()
                        ? "Ninguna"
                        : envio.getListaIncidencias().stream()
                        .map(i -> i.getDescripcion() + " (" + i.getEstadoIncidencia() + ")")
                        .collect(Collectors.joining(" / "));

                String servicios = envio.getListaServiciosAdicionales().isEmpty()
                        ? "Ninguno"
                        : envio.getListaServiciosAdicionales().stream()
                        .map(s -> s.getTipoServicio() + " ($" + String.format("%.0f", s.getCostoServicioAdd()) + ")")
                        .collect(Collectors.joining(" / "));

                writer.append(String.join(",",
                                envio.getIdEnvio(),
                                envio.getEstado().name(),
                                envio.getFechaCreacion().toString(),
                                envio.getFechaEstimadaEntrega().toString(),
                                String.format("%.2f", envio.getCosto()),
                                "\"" + incidencias + "\"",
                                "\"" + servicios + "\""))
                        .append("\n");
            }

            System.out.println("Archivo CSV exportado en: " + archivoSeleccionado.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al exportar CSV: " + e.getMessage());
        }
    }
}