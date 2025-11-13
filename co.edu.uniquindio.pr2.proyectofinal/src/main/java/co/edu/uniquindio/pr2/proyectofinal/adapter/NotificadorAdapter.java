package co.edu.uniquindio.pr2.proyectofinal.adapter;

public class NotificadorAdapter implements INotificador {
    private final NotificadorExterno notificadorExterno;

    public NotificadorAdapter(NotificadorExterno notificadorExterno) {
        this.notificadorExterno = notificadorExterno;
    }

    @Override
    public void notificar(String mensaje) {
        notificadorExterno.enviarMensaje(mensaje);
    }
}