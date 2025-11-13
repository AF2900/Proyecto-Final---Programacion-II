package co.edu.uniquindio.pr2.proyectofinal.command;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;

public class CrearEnvioCommand implements IEnvioOperacion {
    private final EnvioReceiver receiver;
    private final Envio envio;

    public CrearEnvioCommand(EnvioReceiver receiver, Envio envio) {
        this.receiver = receiver;
        this.envio = envio;
    }

    @Override
    public void execute() {
        receiver.crearEnvio(envio);
    }
}