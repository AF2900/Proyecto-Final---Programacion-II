package co.edu.uniquindio.pr2.proyectofinal.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class DataInitializer {
    private List<Usuario> usuarios;
    private List<Repartidor> repartidores;
    private List<Envio> envios;
    private List<Pago> pagos;

    public DataInitializer() {
        usuarios = new ArrayList<>();
        repartidores = new ArrayList<>();
        envios = new ArrayList<>();
        pagos = new ArrayList<>();

        inicializarUsuarios();
        inicializarRepartidores();
        inicializarEnvios();
        inicializarPagos();
    }

        private void inicializarUsuarios() {
            Usuario u1 = new Usuario("U001", "Laura Martínez", "laura@gmail.com", "321567890");
            Usuario u2 = new Usuario("U002", "Andrés López", "andres@gmail.com", "310987654");

            u1.addDireccion(new Direccion("D001", "Casa", "Calle 10 #12-45", "Armenia", 4.538, -75.672));
            u1.addDireccion(new Direccion("D002", "Oficina", "Cra 15 #23-10", "Calarcá", 4.533, -75.657));

            u2.addDireccion(new Direccion("D003", "Apartamento", "Av Bolívar #8-20", "Armenia", 4.540, -75.665));

            usuarios.add(u1);
            usuarios.add(u2);
    }
    private void inicializarRepartidores() {
        Repartidor r1 = new Repartidor("R001", "Carlos Gómez", "carlos@empresa.com", "314567890",
                DisponibilidadRepartidor.DISPONIBLE, "Zona Armenia");
        Repartidor r2 = new Repartidor("R002", "María Torres", "maria@empresa.com", "315654321",
                DisponibilidadRepartidor.DISPONIBLE, "Zona Calarcá");

        repartidores.add(r1);
        repartidores.add(r2);
    }

    private void inicializarEnvios() {
        Usuario usuario = usuarios.get(0);
        Direccion origen = usuario.getDirecciones().get(0);
        Direccion destino = usuario.getDirecciones().get(1);

        Envio e1 = new Envio("E001", origen, destino, 3.0, 0.4, 0.3, 0.3,
                LocalDate.now(), LocalDate.now().plusDays(2), EstadoEnvio.PENDIENTE);
        e1.setUsuario(usuario);
        e1.calcularCosto();

        Repartidor repartidor = repartidores.get(0);
        repartidor.asignarEnvio(e1);

        envios.add(e1);
    }

    private void inicializarPagos() {
        Envio envio = envios.get(0);

        Pago pago = new Pago("P001", envio.getCosto(), LocalDate.now(), MetodoPago.TARJETA_CREDITO, "APROBADO");
        pagos.add(pago);
    }

    public List<Usuario> getUsuarios() {return usuarios;}

    public List<Repartidor> getRepartidores() {return repartidores;}

    public List<Envio> getEnvios() {return envios;}

    public List<Pago> getPagos() {return pagos;}

    public void mostrarResumen() {
        System.out.println("\n RESUMEN DE DATOS INICIALIZADOS:");
        System.out.println("Usuarios:");
        usuarios.forEach(u -> System.out.println("   - " + u));

        System.out.println("\n Repartidores:");
        repartidores.forEach(r -> System.out.println("   - " + r));

        System.out.println("\n Envíos:");
        envios.forEach(e -> System.out.println("   - " + e));

        System.out.println("\n Pagos:");
        pagos.forEach(p -> System.out.println("   - " + p));
    }
}
