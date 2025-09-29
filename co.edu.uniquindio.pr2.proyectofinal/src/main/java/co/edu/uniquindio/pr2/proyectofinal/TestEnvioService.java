package co.edu.uniquindio.pr2.proyectofinal;

import co.edu.uniquindio.pr2.proyectofinal.model.*;
import co.edu.uniquindio.pr2.proyectofinal.service.LosgisticaService;

import java.time.LocalDate;

public class TestEnvioService {
    public static void main(String[] args) {
        LosgisticaService service = new LosgisticaService();

        System.out.println("=== ENVÍOS INICIALES ===");
        service.listarEnvios().forEach(System.out::println);

        Usuario usuario = service.buscarUsuarioPorId("U002");
        Direccion origen = usuario.getDirecciones().get(0);
        Direccion destino = new Direccion("D010", "Oficina Nueva", "Calle 50 #8-10", "Armenia", 4.54, -75.66);

        Envio envio = service.crearEnvio("E010", origen, destino, 5.0, 0.7, 0.4, 0.4, LocalDate.now().plusDays(4), usuario);
        System.out.println("\n Envío creado:\n" + envio);

        service.asignarRepartidor("Ea10", "R001");

        service.registrarPagoEnvio("P010", envio.getCosto(), LocalDate.now(), MetodoPago.EFECTIVO, "E010");

        service.actualizarEstadoEnvio("E010", EstadoEnvio.ENTREGADO);

        System.out.println("\n Resumen final:");
        service.mostrarResumenGeneral();
    }
}
