package co.edu.uniquindio.pr2.proyectofinal.command;

import java.util.ArrayList;
import java.util.List;

public class EnvioInvoker {
    private final List<IEnvioOperacion> operaciones = new ArrayList<>();

    public void recibirOperacion(IEnvioOperacion operacion) {
        operaciones.add(operacion);
    }

    public void ejecutarOperaciones() {
        operaciones.forEach(IEnvioOperacion::execute);
        operaciones.clear();
    }
}