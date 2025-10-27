package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class CrearEnvioAdminController {

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
    @FXML private Button btnActualizar;

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    private void initialize() {
        cbUsuario.getItems().setAll(modelFactory.getEmpresaLogistica().getUsuarios());
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

        cbRepartidor.getItems().setAll(modelFactory.getEmpresaLogistica().getRepartidores());
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

            String idEnvio = String.valueOf(new Random().nextInt(90000) + 10000);
            Direccion origen = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(origenTexto).build();
            Direccion destino = new DireccionBuilder().idDireccion(UUID.randomUUID().toString()).calle(destinoTexto).build();

            LocalDate fechaCreacion = LocalDate.now();
            LocalDate fechaEstimada = prioridad.equals("Urgente") ? fechaCreacion
                    : prioridad.equals("Alta") ? fechaCreacion.plusDays(1) : fechaCreacion.plusDays(2);

            EstadoEnvio estado = EstadoEnvio.PENDIENTE;
            Envio envio = new Envio(idEnvio, origen, destino, peso, Math.cbrt(volumen), Math.cbrt(volumen),
                    Math.cbrt(volumen), fechaCreacion, fechaEstimada, estado);
            envio.setUsuario(usuario);
            envio.setRepartidor(repartidor);
            double costo = (peso * 0.5) + (volumen * 0.02);
            envio.setCosto(costo);

            if (cbSeguro.isSelected())
                envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                        .idServicioAdd(UUID.randomUUID().toString())
                        .tipoServicio(TipoServicio.SEGURO)
                        .costoServicioAdd(10000).build());
            if (cbFragil.isSelected())
                envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                        .idServicioAdd(UUID.randomUUID().toString())
                        .tipoServicio(TipoServicio.FRAGIL)
                        .costoServicioAdd(5000).build());
            if (cbFirma.isSelected())
                envio.getListaServiciosAdicionales().add(new ServicioAdicionalBuilder()
                        .idServicioAdd(UUID.randomUUID().toString())
                        .tipoServicio(TipoServicio.FIRMA_REQUERIDA)
                        .costoServicioAdd(3000).build());

            modelFactory.getEmpresaLogistica().getEnvios().add(envio);
            mostrarAlerta("Éxito", "Envío creado correctamente.");
            Stage stage = (Stage) txtOrigen.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al crear el envío.");
        }
    }

    private void precargarEnvioUsuario() {
        Usuario usuario = cbUsuario.getValue();
        if (usuario == null) return;

        Optional<Envio> envioOpt = modelFactory.getEmpresaLogistica().getEnvios()
                .stream()
                .filter(e -> e.getUsuario().equals(usuario))
                .findFirst();

        if (envioOpt.isPresent()) {
            Envio envio = envioOpt.get();
            txtOrigen.setText(envio.getOrigen().getCalle());
            txtDestino.setText(envio.getDestino().getCalle());
            txtPeso.setText(String.valueOf(envio.getPeso()));
            txtVolumen.setText(String.valueOf(Math.pow(envio.getAlto(), 3)));
            cbPrioridad.setValue(calcularPrioridad(envio.getFechaCreacion(), envio.getFechaEstimadaEntrega()));
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

    private String calcularPrioridad(LocalDate fechaCreacion, LocalDate fechaEstimada) {
        long dias = fechaEstimada.toEpochDay() - fechaCreacion.toEpochDay();
        if (dias == 0) return "Urgente";
        else if (dias == 1) return "Alta";
        else return "Baja";
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}