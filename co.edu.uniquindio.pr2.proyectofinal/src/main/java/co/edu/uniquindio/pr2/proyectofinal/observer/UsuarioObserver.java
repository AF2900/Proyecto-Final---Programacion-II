package co.edu.uniquindio.pr2.proyectofinal.observer;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class UsuarioObserver implements ObserverEnvio {
    private final Usuario usuario;

    public UsuarioObserver(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void actualizar(Envio envio, EstadoEnvio nuevoEstado) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notificación de envío");
            alert.setHeaderText("Estado de tu envío actualizado");
            alert.setContentText("Hola " + usuario.getNombre() +
                    ", el envío " + envio.getIdEnvio() +
                    " ahora está: " + nuevoEstado);
            alert.showAndWait();
        });
    }
}