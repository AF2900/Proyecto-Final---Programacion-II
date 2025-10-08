package co.edu.uniquindio.pr2.proyectofinal.model;

import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionEnvios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionUsuarios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionPagos;
import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica implements IGestionEnvios, IGestionUsuarios, IGestionPagos {
    private final ModelFactory modelFactory;
    private List<Usuario> usuarios;
    private List<Repartidor> repartidores;
    private List<Envio> envios;
    private List<Pago> pagos;
    private List<Tarifa> tarifas;
    private List<Incidencia> incidencias;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Administrador> administradores;

    public EmpresaLogistica() {
        this.modelFactory = ModelFactory.getInstance();
        this.usuarios = new ArrayList<>();
        this.repartidores = new ArrayList<>();
        this.envios = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    @Override
    public void asignarEnvioARepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().add(envio);
            envio.setRepartidor(repartidor);

            modelFactory.getPlataforma().getEnvios().add(envio);
        }
    }

    @Override
    public void eliminarEnvioDeRepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().remove(envio);
            modelFactory.getPlataforma().getEnvios().remove(envio);
        }
    }

    @Override
    public double calcularVolumen(Envio envio) {
        if (envio == null) return 0.0;
        return envio.getLargo() * envio.getAncho() * envio.getAlto();
    }

    @Override
    public double calcularCostoEnvio(Envio envio) {
        if (envio == null) return 0.0;
        double base = 500;
        double costoPeso = envio.getPeso() * 1500;
        double costoVolumen = calcularVolumen(envio) * 3000;
        double total = base + costoPeso + costoVolumen;
        envio.setCosto(Math.round(total * 100) / 100.0);

        modelFactory.getPlataforma().getEnvios().stream()
                .filter(e -> e.getIdEnvio().equals(envio.getIdEnvio()))
                .findFirst()
                .ifPresent(e -> e.setCosto(envio.getCosto()));

        return envio.getCosto();
    }

    @Override
    public void agregarDireccionAUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null) {
            if (!usuario.getDirecciones().contains(direccion)) {
                usuario.getDirecciones().add(direccion);
                // 🔹 Refleja el cambio en el singleton
                modelFactory.getPlataforma().getUsuarios().stream()
                        .filter(u -> u.getIdUsuario().equals(usuario.getIdUsuario()))
                        .findFirst()
                        .ifPresent(u -> u.getDirecciones().add(direccion));
            }
        }
    }

    @Override
    public void eliminarDireccionDeUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null) {
            usuario.getDirecciones().remove(direccion);
            modelFactory.getPlataforma().getUsuarios().stream()
                    .filter(u -> u.getIdUsuario().equals(usuario.getIdUsuario()))
                    .findFirst()
                    .ifPresent(u -> u.getDirecciones().remove(direccion));
        }
    }

    @Override
    public boolean esPagoAprobado(Pago pago) {
        return pago != null && "APROBADO".equalsIgnoreCase(pago.getResultado());
    }

    @Override
    public void validarPago(Pago pago) {
        if (pago == null) return;
        if (pago.getMonto() > 0) {
            pago.setResultado("APROBADO");
        } else {
            pago.setResultado("RECHAZADO");
        }

        modelFactory.getPlataforma().getPagos().stream()
                .filter(p -> p.getIdPago().equals(pago.getIdPago()))
                .findFirst()
                .ifPresent(p -> p.setResultado(pago.getResultado()));
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Repartidor> getRepartidores() {
        return repartidores;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }
}