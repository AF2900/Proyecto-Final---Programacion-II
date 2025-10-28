module co.edu.uniquindio.pr2.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires org.apache.pdfbox;

    opens co.edu.uniquindio.pr2.proyectofinal.model to javafx.base;
    opens co.edu.uniquindio.pr2.proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.pr2.proyectofinal;
    exports co.edu.uniquindio.pr2.proyectofinal.controller;
    opens co.edu.uniquindio.pr2.proyectofinal.controller to javafx.fxml;
    opens co.edu.uniquindio.pr2.proyectofinal.viewcontroller to javafx.fxml;

}