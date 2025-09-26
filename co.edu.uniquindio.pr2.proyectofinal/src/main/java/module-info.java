module co.edu.uniquindio.pr2.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.pr2.proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.pr2.proyectofinal;
    exports co.edu.uniquindio.pr2.proyectofinal.controller;
    opens co.edu.uniquindio.pr2.proyectofinal.controller to javafx.fxml;
}