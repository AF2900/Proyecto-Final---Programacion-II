package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.CrearEnvioAdminController;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;

public class CrearEnvioAdminViewController {

    @FXML private ComboBox<Usuario> cbUsuario;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private ChoiceBox<String> cbPrioridad;
    @FXML private ComboBox<Repartidor> cbRepartidor;
    @FXML private CheckBox cbSeguro;
    @FXML private CheckBox cbFragil;
    @FXML private CheckBox cbFirma;

    private final CrearEnvioAdminController controller = new CrearEnvioAdminController();

    @FXML
    private void initialize() {
        cbUsuario.getItems().setAll(controller.obtenerUsuarios());
        cbUsuario.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                setText(empty || usuario == null ? null : usuario.getNombre());
            }
        });
        cbUsuario.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                setText(empty || usuario == null ? null : usuario.getNombre());
            }
        });
        cbUsuario.setOnAction(event -> precargarEnvioUsuario());

        cbRepartidor.getItems().setAll(controller.obtenerRepartidores());
        cbRepartidor.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Repartidor repartidor, boolean empty) {
                super.updateItem(repartidor, empty);
                setText(empty || repartidor == null ? null : repartidor.getIdRepartidor() + " - " + repartidor.getDisponibilidadRepartidor());
            }
        });
        cbRepartidor.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Repartidor repartidor, boolean empty) {
                super.updateItem(repartidor, empty);
                setText(empty || repartidor == null ? null : repartidor.getIdRepartidor() + " - " + repartidor.getDisponibilidadRepartidor());
            }
        });

        cbPrioridad.getItems().setAll("Normal", "Alta", "Urgente");
        cbPrioridad.setValue("Normal");
    }

    @FXML
    private void crearEnvio() {
        try {
            Usuario usuario = cbUsuario.getValue();
            Repartidor repartidor = cbRepartidor.getValue();
            if (usuario == null || repartidor == null) {
                mostrarAlerta("Error", "Debe seleccionar un usuario y un repartidor.");
                return;
            }

            String origenTexto = txtOrigen.getText().trim();
            String destinoTexto = txtDestino.getText().trim();
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            String prioridad = cbPrioridad.getValue();

            controller.crearEnvio(usuario, repartidor, origenTexto, destinoTexto, peso, volumen, prioridad,
                    cbSeguro.isSelected(), cbFragil.isSelected(), cbFirma.isSelected());

            mostrarAlerta("Éxito", "Envío creado correctamente.");
            Stage stage = (Stage) txtOrigen.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al crear el envío.");
            e.printStackTrace();
        }
    }

    private void precargarEnvioUsuario() {
        Usuario usuario = cbUsuario.getValue();
        if (usuario == null) return;

        Optional<Envio> envioOpt = controller.obtenerEnvioPorUsuario(usuario);
        if (envioOpt.isPresent()) {
            Envio envio = envioOpt.get();
            txtOrigen.setText(envio.getOrigen().getCalle());
            txtDestino.setText(envio.getDestino().getCalle());
            txtPeso.setText(String.valueOf(envio.getPeso()));
            txtVolumen.setText(String.valueOf(Math.pow(envio.getAlto(), 3)));
            cbPrioridad.setValue(controller.calcularPrioridad(envio.getFechaCreacion(), envio.getFechaEstimadaEntrega()));
            cbRepartidor.setValue(envio.getRepartidor());
            cbSeguro.setSelected(envio.getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.SEGURO));
            cbFragil.setSelected(envio.getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FRAGIL));
            cbFirma.setSelected(envio.getListaServiciosAdicionales().stream().anyMatch(s -> s.getTipoServicio() == TipoServicio.FIRMA_REQUERIDA));
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtOrigen.clear();
        txtDestino.clear();
        txtPeso.clear();
        txtVolumen.clear();
        cbPrioridad.setValue("Baja");
        cbRepartidor.setValue(null);
        cbSeguro.setSelected(false);
        cbFragil.setSelected(false);
        cbFirma.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}