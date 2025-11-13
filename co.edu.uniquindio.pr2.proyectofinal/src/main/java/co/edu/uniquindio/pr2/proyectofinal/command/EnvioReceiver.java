package co.edu.uniquindio.pr2.proyectofinal.command;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;

public class EnvioReceiver {
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public void crearEnvio(Envio envio) {
        modelFactory.getEmpresaLogistica().getEnvios().add(envio);
        System.out.println("[COMANDO CREAR ENVÍO] Envío agregado con ID: " + envio.getIdEnvio());
    }

    public void cancelarEnvio(String idEnvio) {
        modelFactory.getEmpresaLogistica().getEnvios().removeIf(e -> e.getIdEnvio().equals(idEnvio));
        System.out.println("[COMANDO CANCELAR ENVÍO] Envío eliminado con ID: " + idEnvio);
    }

    public void actualizarEstado(Envio envio, EstadoEnvio nuevoEstado) {
        envio.setEstado(nuevoEstado);
        System.out.println("[COMANDO ACTUALIZAR ESTADO] Envío " + envio.getIdEnvio() + " nuevo estado: " + nuevoEstado);
    }
}