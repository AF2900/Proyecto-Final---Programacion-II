package co.edu.uniquindio.pr2.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogisticaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("/co/edu/uniquindio/pr2/proyectofinal/usuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CRUD Usuario");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
