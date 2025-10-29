package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.CrearEnvioController;
import co.edu.uniquindio.pr2.proyectofinal.model.MetodoPago;
import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CrearEnvioViewController {

    @FXML private TextField txtOrigen, txtDestino, txtPeso, txtVolumen;
    @FXML private ChoiceBox<String> cbPrioridad;
    @FXML private ChoiceBox<MetodoPago> cbMetodoPago;
    @FXML private CheckBox cbSeguro, cbFragil, cbFirma;
    @FXML private Label lblEstado;
    @FXML private Button btnConfirmar, btnVolver;

    private CrearEnvioController controller;
    private UsuarioMenuViewController usuarioMenuViewController;

    @FXML
    private void initialize() {
        controller = new CrearEnvioController();
        cbMetodoPago.getItems().setAll(MetodoPago.values());
        btnConfirmar.setOnAction(event -> crearEnvio());
        btnVolver.setOnAction(event -> volverMenu());
    }

    public void setUsuarioMenuViewController(UsuarioMenuViewController usuarioMenuViewController) {
        this.usuarioMenuViewController = usuarioMenuViewController;
    }

    private void crearEnvio() {
        try {
            String origen = txtOrigen.getText().trim();
            String destino = txtDestino.getText().trim();
            String pesoTxt = txtPeso.getText().trim();
            String volumenTxt = txtVolumen.getText().trim();
            String prioridad = cbPrioridad.getValue();
            MetodoPago metodoPago = cbMetodoPago.getValue();

            var validacion = controller.validarCampos(origen, destino, pesoTxt, volumenTxt, prioridad, metodoPago);
            if (validacion.isPresent()) {
                mostrarAlerta("Campos incompletos", validacion.get());
                return;
            }

            double peso = Double.parseDouble(pesoTxt);
            double volumen = Double.parseDouble(volumenTxt);

            double costoEstimado = controller.calcularCostoTotal(prioridad, peso, volumen, cbSeguro.isSelected(), cbFragil.isSelected(), cbFirma.isSelected());

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar envío");
            confirm.setHeaderText(null);
            confirm.setContentText("¿Está seguro de realizar el envío?\nCosto total: $" + String.format("%.2f", costoEstimado));
            if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

            EnviosViewUpdate(origen, destino, peso, volumen, prioridad, metodoPago);

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al crear el envío.");
        }
    }

    private void EnviosViewUpdate(String origen, String destino, double peso, double volumen, String prioridad, MetodoPago metodoPago) {
        Envio envio = controller.crearEnvio(origen, destino, peso, volumen, prioridad,
                cbSeguro.isSelected(), cbFragil.isSelected(), cbFirma.isSelected(), metodoPago);

        if (envio.getRepartidor() != null)
            lblEstado.setText("Envío creado y asignado a: " + envio.getRepartidor().getNombre());
        else
            lblEstado.setText("Envío creado — No hay repartidores disponibles.");

        mostrarAlerta("Éxito", "Envío creado exitosamente. Código: " + envio.getIdEnvio());

        if (usuarioMenuViewController != null) {
            usuarioMenuViewController.actualizarResumenEnvios(controller.getUsuarioActual());
            usuarioMenuViewController.cargarEnviosRecientes(controller.getUsuarioActual());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void volverMenu() {
        txtOrigen.getScene().getWindow().hide();
    }
}