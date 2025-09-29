package co.edu.uniquindio.pr2.proyectofinal.service;import co.edu.uniquindio.pr2.proyectofinal.model.*;

import java.time.LocalDate;
import java.util.List;

public class LosgisticaService {
    private DataInitializer dataInitializer;

    public void LogisticaService() {this.dataInitializer = new DataInitializer();}


    public List<Usuario> listarUsuarios() {return dataInitializer.getUsuarios();}

    public Usuario buscarUsuarioPorId(String idUsuario) {
        return dataInitializer.getUsuarios()
                .stream()
                .filter(u -> u.getIdUsuario().equalsIgnoreCase(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public void agregarUsuario(String idUsuario, String nombre, String correo, String telefono) {
        Usuario nuevo = new Usuario(idUsuario, nombre, correo, telefono);
        dataInitializer.getUsuarios().add(nuevo);
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario u = buscarUsuarioPorId(idUsuario);
        return u != null && dataInitializer.getUsuarios().remove(u);
    }

    public List<Repartidor> listarRepartidores() {return dataInitializer.getRepartidores();}

    public Repartidor buscarRepartidorPorId(String idRepartidor) {
        return dataInitializer.getRepartidores()
                .stream()
                .filter(r -> r.getIdRepartidor().equalsIgnoreCase(idRepartidor))
                .findFirst()
                .orElse(null);
    }

    public List<Envio> listarEnvios() {return dataInitializer.getEnvios();}

    public Envio buscarEnvioPorId(String idEnvio) {
        return dataInitializer.getEnvios()
                .stream()
                .filter(e -> e.getIdEnvio().equalsIgnoreCase(idEnvio))
                .findFirst()
                .orElse(null);
    }

    public Envio crearEnvio(String idEnvio, Direccion origen, Direccion destino, double peso, double largo, double ancho, double alto,
                            LocalDate fechaEstimadaEntrega, Usuario usuario) {

        Envio envio = new Envio(idEnvio, origen, destino, peso, largo, ancho, alto,
                LocalDate.now(), fechaEstimadaEntrega, EstadoEnvio.PENDIENTE);

        envio.setUsuario(usuario);
        envio.calcularCosto();
        dataInitializer.getEnvios().add(envio);

        return envio;
    }

    public boolean actualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) {
        Envio envio = buscarEnvioPorId(idEnvio);
        if (envio != null) {
            envio.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public boolean asignarRepartidor(String idEnvio, String idRepartidor) {
        Envio envio = buscarEnvioPorId(idEnvio);
        Repartidor repartidor = buscarRepartidorPorId(idRepartidor);

        if (envio != null && repartidor != null) {
            repartidor.asignarEnvio(envio);
            envio.setRepartidor(repartidor);
            envio.setEstado(EstadoEnvio.EN_CAMINO);
            return true;
        }
        return false;
    }

    public boolean eliminarEnvio(String idEnvio) {
        Envio envio = buscarEnvioPorId(idEnvio);
        return envio != null && dataInitializer.getEnvios().remove(envio);
    }

    public List<Pago> listarPagos() {return dataInitializer.getPagos();}

    public Pago registrarPagoEnvio(String idPago, double monto, LocalDate fecha,
                                   MetodoPago metodoPago, String idEnvio) {

        Envio envio = buscarEnvioPorId(idEnvio);
        if (envio == null) return null;

        Pago pago = new Pago(idPago, monto, fecha, metodoPago, "PENDIENTE");
        pago.validarPago();

        dataInitializer.getPagos().add(pago);

        if (pago.esPagoAprobado()) {
            envio.setEstado(EstadoEnvio.EN_CAMINO);
        } else {
            envio.setEstado(EstadoEnvio.CANCELADO);
        }

        return pago;
    }

    public void mostrarResumenGeneral() {
        dataInitializer.mostrarResumen();
    }
}
