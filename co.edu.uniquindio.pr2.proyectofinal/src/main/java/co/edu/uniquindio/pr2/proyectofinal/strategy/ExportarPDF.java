package co.edu.uniquindio.pr2.proyectofinal.strategy;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Incidencia;
import co.edu.uniquindio.pr2.proyectofinal.model.ServicioAdicional;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ExportarPDF implements IExportarStrategy {

    @Override
    public void exportar(List<Envio> envios) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Historial como PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName("HistorialEnvios.pdf");

        File archivoSeleccionado = fileChooser.showSaveDialog(new Stage());
        if (archivoSeleccionado == null) return;

        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

            contenido.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contenido.beginText();
            contenido.newLineAtOffset(50, 750);
            contenido.showText("Historial de Envíos");
            contenido.endText();

            contenido.setFont(PDType1Font.HELVETICA, 10);
            int y = 720;

            for (Envio envio : envios) {
                if (y < 100) {
                    contenido.close();
                    pagina = new PDPage();
                    documento.addPage(pagina);
                    contenido = new PDPageContentStream(documento, pagina);
                    contenido.setFont(PDType1Font.HELVETICA, 10);
                    y = 750;
                }

                String incidencias = envio.getListaIncidencias().isEmpty()
                        ? "Ninguna"
                        : envio.getListaIncidencias().stream()
                        .map(i -> i.getDescripcion() + " (" + i.getEstadoIncidencia() + ")")
                        .collect(Collectors.joining(", "));

                String servicios = envio.getListaServiciosAdicionales().isEmpty()
                        ? "Ninguno"
                        : envio.getListaServiciosAdicionales().stream()
                        .map(s -> s.getTipoServicio() + " ($" + String.format("%.0f", s.getCostoServicioAdd()) + ")")
                        .collect(Collectors.joining(", "));

                contenido.beginText();
                contenido.newLineAtOffset(50, y);
                contenido.showText(String.format(
                        "Código: %s | Estado: %s | Creado: %s | Entrega: %s | Costo: $%.2f",
                        envio.getIdEnvio(), envio.getEstado().name(),
                        envio.getFechaCreacion(), envio.getFechaEstimadaEntrega(),
                        envio.getCosto()));
                contenido.endText();
                y -= 15;

                contenido.beginText();
                contenido.newLineAtOffset(60, y);
                contenido.showText("Incidencias: " + incidencias);
                contenido.endText();
                y -= 15;

                contenido.beginText();
                contenido.newLineAtOffset(60, y);
                contenido.showText("Servicios Adicionales: " + servicios);
                contenido.endText();
                y -= 25;
            }

            contenido.close();
            documento.save(archivoSeleccionado);
            System.out.println("Archivo PDF exportado en: " + archivoSeleccionado.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al exportar PDF: " + e.getMessage());
        }
    }
}