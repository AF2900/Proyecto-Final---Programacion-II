package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CotizarEnvioController {

    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private ChoiceBox<String> cbPrioridad;
    @FXML private CheckBox cbSeguro;
    @FXML private CheckBox cbFragil;
    @FXML private CheckBox cbFirma;

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    void calcularCosto() {
        try {
            String origen = txtOrigen.getText().trim();
            String destino = txtDestino.getText().trim();
            String pesoTexto = txtPeso.getText().trim();
            String volumenTexto = txtVolumen.getText().trim();
            String prioridad = cbPrioridad.getValue();

            if (origen.isEmpty() || destino.isEmpty() || pesoTexto.isEmpty() || volumenTexto.isEmpty() || prioridad == null) {
                mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
                return;
            }

            double peso, volumen;
            try {
                peso = Double.parseDouble(pesoTexto);
                volumen = Double.parseDouble(volumenTexto);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "Peso y volumen deben ser valores numéricos.");
                return;
            }

            double costoBase = (peso * 0.5) + (volumen * 0.02);
            double costo;
            switch (prioridad) {
                case "Alta" -> costo = costoBase * 1.4;
                case "Urgente" -> costo = costoBase * 1.8;
                default -> costo = costoBase;
            }

            StringBuilder servicios = new StringBuilder();
            if (cbSeguro.isSelected()) {
                costo += 10000;
                servicios.append("Seguro");
            }
            if (cbFragil.isSelected()) {
                costo += 5000;
                if (servicios.length() > 0) servicios.append(", ");
                servicios.append("Frágil");
            }
            if (cbFirma.isSelected()) {
                costo += 3000;
                if (servicios.length() > 0) servicios.append(", ");
                servicios.append("Firma requerida");
            }

            String resumen = """
                    Cotización de envío:
                    Origen: %s
                    Destino: %s
                    Prioridad: %s
                    Servicios adicionales: %s
                    Costo estimado: $%.2f
                    """.formatted(
                    origen,
                    destino,
                    prioridad,
                    servicios.length() > 0 ? servicios.toString() : "Ninguno",
                    costo
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cotización de Envío");
            alert.setHeaderText("Cotización Calculada");
            alert.setContentText(resumen);
            alert.showAndWait();

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al calcular la cotización.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void volverMenuUsuario() {
        Stage stage = (Stage) txtOrigen.getScene().getWindow();
        stage.close();
    }
}