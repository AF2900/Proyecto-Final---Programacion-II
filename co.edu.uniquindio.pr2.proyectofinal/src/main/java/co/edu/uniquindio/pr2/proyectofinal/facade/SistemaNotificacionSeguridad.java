package co.edu.uniquindio.pr2.proyectofinal.facade;

public class SistemaNotificacionSeguridad {

    public void notificarAccesoExitoso() {
        System.out.println("Acceso autorizado al panel de administrador");
    }

    public void notificarAccesoDenegado() {
        System.out.println("Clave incorrecta. Acceso denegado");
    }
}