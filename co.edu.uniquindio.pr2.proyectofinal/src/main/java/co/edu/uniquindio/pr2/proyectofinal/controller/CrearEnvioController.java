package co.edu.uniquindio.pr2.proyectofinal.controller;

import co.edu.uniquindio.pr2.proyectofinal.LogisticaApplication;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.builder.DireccionBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.ServicioAdicionalBuilder;
import co.edu.uniquindio.pr2.proyectofinal.builder.PagoBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class CrearEnvioController {

    @FXML
    private TextField txtOrigen;
    @FXML
    private TextField txtDestino;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtVolumen;
    @FXML
    private ChoiceBox<String> cbPrioridad;
    @FXML
    private ChoiceBox<MetodoPago> cbMetodoPago;
    @FXML
    private CheckBox cbSeguro;
    @FXML
    private CheckBox cbFragil;
    @FXML
    private CheckBox cbFirma;
    @FXML
    private Label lblEstado;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnVolver;

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    private void initialize() {
        cbMetodoPago.getItems().setAll(MetodoPago.values());
    }

    @FXML
    private void crearEnvio() {
        try {
            String origenTexto = txtOrigen.getText().trim();
            String destinoTexto = txtDestino.getText().trim();
            String pesoTexto = txtPeso.getText().trim();
            String volumenTexto = txtVolumen.getText().trim();
            String prioridad = cbPrioridad.getValue();
            MetodoPago metodoPago = cbMetodoPago.getValue();

            if (origenTexto.isEmpty() || destinoTexto.isEmpty() || pesoTexto.isEmpty() || volumenTexto.isEmpty() || prioridad == null || metodoPago == null) {
                mostrarAlerta("Campos incompletos", "Por favor llena todos los campos.");
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

            int codigo = new Random().nextInt(90000) + 10000;
            String idEnvio = String.valueOf(codigo);

            Direccion origen = new DireccionBuilder()
                    .idDireccion(UUID.randomUUID().toString())
                    .calle(origenTexto)
                    .build();

            Direccion destino = new DireccionBuilder()
                    .idDireccion(UUID.randomUUID().toString())
                    .calle(destinoTexto)
                    .build();

            LocalDate fechaCreacion = LocalDate.now();
            LocalDate fechaEstimada;
            EstadoEnvio estadoEnvio;

            switch (prioridad) {
                case "Alta" -> {
                    fechaEstimada = fechaCreacion.plusDays(1);
                    estadoEnvio = EstadoEnvio.EN_CAMINO;
                }
                case "Urgente" -> {
                    fechaEstimada = fechaCreacion;
                    estadoEnvio = EstadoEnvio.ENTREGADO;
                }
                default -> {
                    fechaEstimada = fechaCreacion.plusDays(2);
                    estadoEnvio = EstadoEnvio.PENDIENTE;
                }
            }

            double lado = Math.cbrt(volumen);
            double largo = Math.round(lado * 100.0) / 100.0;
            double ancho = Math.round(lado * 100.0) / 100.0;
            double alto = Math.round(lado * 100.0) / 100.0;

            Envio envio = new Envio(
                    idEnvio,
                    origen,
                    destino,
                    peso,
                    largo,
                    ancho,
                    alto,
                    fechaCreacion,
                    fechaEstimada,
                    estadoEnvio
            );

            double costoBase = (peso * 0.5) + (volumen * 0.02);
            double costo;
            switch (prioridad) {
                case "Alta" -> costo = costoBase * 1.4;
                case "Urgente" -> costo = costoBase * 1.8;
                default -> costo = costoBase;
            }
            envio.setCosto(costo);

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar pago");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("El costo del envío es: $" + String.format("%.2f", costo) + "\n¿Está seguro de que desea hacer este envío?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isEmpty() || resultado.get() != ButtonType.OK) return;

            Usuario usuario = modelFactory.getUsuarioActual();
            if (usuario != null) envio.setUsuario(usuario);

            if (cbSeguro.isSelected())
                envio.getListaServiciosAdicionales().add(
                        new ServicioAdicionalBuilder()
                                .idServicioAdd(UUID.randomUUID().toString())
                                .tipoServicio("Seguro")
                                .costoServicioAdd(10000)
                                .build());
            if (cbFragil.isSelected())
                envio.getListaServiciosAdicionales().add(
                        new ServicioAdicionalBuilder()
                                .idServicioAdd(UUID.randomUUID().toString())
                                .tipoServicio("Frágil")
                                .costoServicioAdd(5000)
                                .build());
            if (cbFirma.isSelected())
                envio.getListaServiciosAdicionales().add(
                        new ServicioAdicionalBuilder()
                                .idServicioAdd(UUID.randomUUID().toString())
                                .tipoServicio("Firma requerida")
                                .costoServicioAdd(3000)
                                .build());

            Optional<Repartidor> repartidorDisponible = modelFactory.getEmpresaLogistica()
                    .getRepartidores()
                    .stream()
                    .filter(r -> r.getDisponibilidadRepartidor() == DisponibilidadRepartidor.DISPONIBLE)
                    .findFirst();

            if (repartidorDisponible.isPresent()) {
                Repartidor r = repartidorDisponible.get();
                envio.setRepartidor(r);
                r.getEnviosAsignados().add(envio);
                r.setDisponibilidadRepartidor(DisponibilidadRepartidor.OCUPADO);
                lblEstado.setText("Envío creado y asignado a: " + r.getNombre());
            } else {
                lblEstado.setText("Envío creado — No hay repartidores disponibles.");
            }

            Pago pago = new PagoBuilder()
                    .idPago(UUID.randomUUID().toString())
                    .monto(costo)
                    .fecha(LocalDate.now())
                    .metodoPago(metodoPago)
                    .resultado("APROBADO")
                    .build();

            modelFactory.getEmpresaLogistica().getPagos().add(pago);
            modelFactory.getEmpresaLogistica().getEnvios().add(envio);

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Éxito");
            exito.setHeaderText(null);
            exito.setContentText("Envío pagado y creado con éxito.\nCódigo de envío: " + idEnvio);
            exito.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al crear el envío.");
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