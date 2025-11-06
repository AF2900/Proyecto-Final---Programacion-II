package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.CotizarEnvioController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CotizarEnvioViewController {

    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private ChoiceBox<String> cbPrioridad;
    @FXML private CheckBox cbSeguro;
    @FXML private CheckBox cbFragil;
    @FXML private CheckBox cbFirma;

    private final CotizarEnvioController cotizarEnvioController = new CotizarEnvioController();

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

            boolean seguro = cbSeguro.isSelected();
            boolean fragil = cbFragil.isSelected();
            boolean firma = cbFirma.isSelected();

            double costo = cotizarEnvioController.calcularCostoEnvio(origen, destino, peso, volumen, prioridad, seguro, fragil, firma);

            StringBuilder servicios = new StringBuilder();
            if (seguro) servicios.append("Seguro");
            if (fragil) {
                if (servicios.length() > 0) servicios.append(", ");
                servicios.append("Frágil");
            }
            if (firma) {
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

    @FXML
    void volverMenuUsuario() {
        Stage stage = (Stage) txtOrigen.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
