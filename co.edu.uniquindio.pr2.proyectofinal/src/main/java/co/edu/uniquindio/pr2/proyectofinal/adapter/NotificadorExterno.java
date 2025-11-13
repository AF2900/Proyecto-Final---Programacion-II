package co.edu.uniquindio.pr2.proyectofinal.adapter;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class NotificadorExterno {
    public void enviarMensaje(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notificación");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}