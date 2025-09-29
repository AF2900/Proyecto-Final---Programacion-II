package co.edu.uniquindio.pr2.proyectofinal;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import java.time.LocalDate;

public class MainPrueba {
    public static void main(String[] args) {

        Usuario usuario = new Usuario("U001", "Laura Martínez", "laura@gmail.com", "321567890");
        System.out.println("Usuario creado" + usuario);

        Direccion direcionOrigen = new Direccion("D001", "Casa", "Calle 10 #12-45", "Armenia", 4.538, -75.672);
        Direccion direccionDestino = new Direccion("D002", "Oficina", "Cra 15 #23-10", "Calarcá", 4.533, -75.657);

        usuario.addDireccion(direcionOrigen);
        usuario.addDireccion(direccionDestino);

        System.out.println("Dirrecion aociadas al usuario");
        for (Direccion d : usuario.getDirecciones()) {
            System.out.println("   - " + d);
        }

        Repartidor repartidor = new Repartidor("R001", "Carlos Gómez", "carlos@empresa.com",
                "314567890", DisponibilidadRepartidor.DISPONIBLE, "Zona Armenia-Calarcá");

        System.out.println("\n Repartidor creado " + repartidor);

        Envio envio = new Envio("E001", direcionOrigen, direccionDestino, 2.5, 0.5, 0.4, 0.3,
                LocalDate.now(), LocalDate.now().plusDays(2), EstadoEnvio.PENDIENTE);

        envio.setUsuario(usuario);
        envio.calcularCosto();

        System.out.println("\n Envio creado: ");
        System.out.println(envio);

        repartidor.asignarEnvio(envio);

        System.out.println("\n Envios asignado al repartidor: ");
        for (Envio e : repartidor.getEnviosAsignados()) {
            System.out.println("   - " + e.getIdEnvio() + " | Estado: " + e.getEstado() + " | Costo: $" + e.getCosto());
        }

        Pago pago = new Pago("P001", envio.getCosto(), LocalDate.now(), MetodoPago.TARJETA_CREDITO, "PENDIENTE");
        pago.validarPago();

        System.out.println("\n Pago registrado");
        System.out.println(pago);

        if (pago.esPagoAprobado()) {
            envio.setEstado(EstadoEnvio.EN_CAMINO);
            System.out.println("\n Pago aprobado. El envio ha cambiado a estado: " + envio.getEstado());
        } else {
            envio.setEstado(EstadoEnvio.CANCELADO);
            System.out.println("\n Pago rechazado. El envio fue cancelado");
        }
        System.out.println("\n Resumen final de envio");
        System.out.println(envio);
    }
}
