package co.edu.uniquindio.pr2.proyectofinal.viewcontroller;

import co.edu.uniquindio.pr2.proyectofinal.controller.GestionEnviosController;
import co.edu.uniquindio.pr2.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GestionEnviosViewController {

    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, String> colCodigo;
    @FXML private TableColumn<Envio, String> colUsuario;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, String> colDestino;
    @FXML private TableColumn<Envio, Double> colPeso;
    @FXML private TableColumn<Envio, Double> colVolumen;
    @FXML private TableColumn<Envio, Repartidor> colRepartidor;
    @FXML private TableColumn<Envio, Boolean> colSeguro;
    @FXML private TableColumn<Envio, Boolean> colFragil;
    @FXML private TableColumn<Envio, Boolean> colFirma;
    @FXML private TableColumn<Envio, EstadoEnvio> colEstado;
    @FXML private TableColumn<Envio, EstadoIncidencia> colIncidencias;
    @FXML private TableColumn<Envio, java.time.LocalDate> colEntrega;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private TableColumn<Envio, String> colFecha;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnEliminar;
    @FXML private Button btnNuevoEnvio;

    private GestionEnviosController gestionEnviosController;

    @FXML
    private void initialize() {
        gestionEnviosController = new GestionEnviosController(tablaEnvios, colCodigo, colUsuario, colOrigen, colDestino, colPeso, colVolumen, colRepartidor, colSeguro, colFragil, colFirma, colEstado, colIncidencias, colEntrega, colCosto, colFecha, txtBuscar);
        gestionEnviosController.inicializarTabla();
        btnBuscar.setOnAction(event -> gestionEnviosController.buscarEnvio());
        btnEliminar.setOnAction(event -> gestionEnviosController.eliminarEnvio());
        btnNuevoEnvio.setOnAction(event -> gestionEnviosController.crearEnvio());
    }
}