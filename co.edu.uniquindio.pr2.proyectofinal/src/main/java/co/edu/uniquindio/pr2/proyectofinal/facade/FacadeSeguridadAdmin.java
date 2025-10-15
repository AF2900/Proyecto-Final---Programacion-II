package co.edu.uniquindio.pr2.proyectofinal.facade;

public class FacadeSeguridadAdmin {

    private final SistemaValidacionClave sistemaValidacion;
    private final SistemaRegistroIntentos sistemaRegistro;
    private final SistemaNotificacionSeguridad sistemaNotificacion;

    public FacadeSeguridadAdmin() {
        sistemaValidacion = new SistemaValidacionClave();
        sistemaRegistro = new SistemaRegistroIntentos();
        sistemaNotificacion = new SistemaNotificacionSeguridad();
    }

    public boolean intentarAcceso(String claveIngresada) {
        System.out.println("Iniciando proceso de validación de acceso administrador...");
        boolean esValida = sistemaValidacion.validarClave(claveIngresada);

        if (esValida) {
            sistemaNotificacion.notificarAccesoExitoso();
            sistemaRegistro.reiniciarIntentos();
            return true;
        } else {
            sistemaRegistro.registrarIntento();
            sistemaNotificacion.notificarAccesoDenegado();
            return false;
        }
    }
}