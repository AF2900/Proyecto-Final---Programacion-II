package co.edu.uniquindio.pr2.proyectofinal;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LogisticaApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ModelFactory.getInstance().inicializarDatos();

        FXMLLoader fxmlLoader = new FXMLLoader(LogisticaApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Sistema de Empresa Logística");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}