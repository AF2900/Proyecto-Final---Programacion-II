package co.edu.uniquindio.pr2.proyectofinal.model;

import co.edu.uniquindio.pr2.proyectofinal.builder.*;
import co.edu.uniquindio.pr2.proyectofinal.factory.ModelFactory;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionEnvios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionUsuarios;
import co.edu.uniquindio.pr2.proyectofinal.services.IGestionPagos;
import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica implements IGestionEnvios, IGestionUsuarios, IGestionPagos {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Administrador> administradores = new ArrayList<>();
    private final List<Repartidor> repartidores = new ArrayList<>();
    private final List<Envio> envios = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();
    private final List<Tarifa> tarifas = new ArrayList<>();
    private final List<Incidencia> incidencias = new ArrayList<>();
    private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

    public EmpresaLogistica() {}

    public Usuario buscarUsuarioPorCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return null;
        return usuarios.stream()
                .filter(u -> correo.equalsIgnoreCase(u.getCorreo()))
                .findFirst()
                .orElse(null);
    }

    public Administrador buscarAdministradorPorCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return null;
        return administradores.stream()
                .filter(a -> correo.equalsIgnoreCase(a.getCorreo()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void asignarEnvioARepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().add(envio);
            envio.setRepartidor(repartidor);
            envios.add(envio);
        }
    }

    @Override
    public void eliminarEnvioDeRepartidor(Repartidor repartidor, Envio envio) {
        if (repartidor != null && envio != null) {
            repartidor.getEnviosAsignados().remove(envio);
            envios.remove(envio);
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
        return envio.getCosto();
    }

    @Override
    public boolean esPagoAprobado(Pago pago) {
        return pago != null && "APROBADO".equalsIgnoreCase(pago.getResultado());
    }

    @Override
    public void validarPago(Pago pago) {
        if (pago != null) {
            pago.setResultado(pago.getMonto() > 0 ? "APROBADO" : "RECHAZADO");
        }
    }

    @Override
    public void agregarDireccionAUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null && !usuario.getDirecciones().contains(direccion)) {
            usuario.getDirecciones().add(direccion);
        }
    }

    @Override
    public void eliminarDireccionDeUsuario(Usuario usuario, Direccion direccion) {
        if (usuario != null && direccion != null) {
            usuario.getDirecciones().remove(direccion);
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
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

    public void agregarUsuario(Usuario usuario) {
        if (usuario != null && buscarUsuarioPorCorreo(usuario.getCorreo()) == null) {
            usuarios.add(new UsuarioBuilder().from(usuario).build());
        }
    }

    public void agregarAdministrador(Administrador admin) {
        if (admin != null) {
            administradores.add(new AdministradorBuilder().from(admin).build());
        }
    }

    public void agregarRepartidor(Repartidor repartidor) {
        if (repartidor != null) {
            repartidores.add(new RepartidorBuilder().from(repartidor).build());
        }
    }

    public void agregarEnvio(Envio envio) {
        if (envio != null) {
            envios.add(new EnvioBuilder().from(envio).build());
        }
    }

    public void agregarPago(Pago pago) {
        if (pago != null) {
            pagos.add(new PagoBuilder().from(pago).build());
        }
    }

    public void agregarTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            tarifas.add(new TarifaBuilder().from(tarifa).build());
        }
    }

    public void agregarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            incidencias.add(new IncidenciaBuilder().from(incidencia).build());
        }
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            serviciosAdicionales.add(new ServicioAdicionalBuilder().from(servicio).build());
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        if (usuario != null) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getIdUsuario().equals(usuario.getIdUsuario())) {
                    usuarios.set(i, usuario);
                    break;
                }
            }
        }
    }

    public void actualizarAdministrador(Administrador admin) {
        if (admin != null) {
            for (int i = 0; i < administradores.size(); i++) {
                if (administradores.get(i).getIdAdministrador().equals(admin.getIdAdministrador())) {
                    administradores.set(i, admin);
                    break;
                }
            }
        }
    }

    public void actualizarRepartidor(Repartidor repartidor) {
        if (repartidor != null) {
            for (int i = 0; i < repartidores.size(); i++) {
                if (repartidores.get(i).getIdRepartidor().equals(repartidor.getIdRepartidor())) {
                    repartidores.set(i, repartidor);
                    break;
                }
            }
        }
    }

    public void actualizarEnvio(Envio envio) {
        if (envio != null) {
            for (int i = 0; i < envios.size(); i++) {
                if (envios.get(i).getIdEnvio().equals(envio.getIdEnvio())) {
                    envios.set(i, envio);
                    break;
                }
            }
        }
    }

    public void actualizarPago(Pago pago) {
        if (pago != null) {
            for (int i = 0; i < pagos.size(); i++) {
                if (pagos.get(i).getIdPago().equals(pago.getIdPago())) {
                    pagos.set(i, pago);
                    break;
                }
            }
        }
    }

    public void actualizarTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            for (int i = 0; i < tarifas.size(); i++) {
                if (tarifas.get(i).getIdTarifa().equals(tarifa.getIdTarifa())) {
                    tarifas.set(i, tarifa);
                    break;
                }
            }
        }
    }

    public void actualizarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            for (int i = 0; i < incidencias.size(); i++) {
                if (incidencias.get(i).getIdIncidencia().equals(incidencia.getIdIncidencia())) {
                    incidencias.set(i, incidencia);
                    break;
                }
            }
        }
    }

    public void actualizarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            for (int i = 0; i < serviciosAdicionales.size(); i++) {
                if (serviciosAdicionales.get(i).getIdServicioAdd().equals(servicio.getIdServicioAdd())) {
                    serviciosAdicionales.set(i, servicio);
                    break;
                }
            }
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    public void eliminarAdministrador(Administrador admin) {
        administradores.remove(admin);
    }

    public void eliminarRepartidor(Repartidor repartidor) {
        repartidores.remove(repartidor);
    }

    public void eliminarEnvio(Envio envio) {
        envios.remove(envio);
    }

    public void eliminarPago(Pago pago) {
        pagos.remove(pago);
    }

    public void eliminarTarifa(Tarifa tarifa) {
        tarifas.remove(tarifa); }

    public void eliminarIncidencia(Incidencia incidencia) {
        incidencias.remove(incidencia);
    }

    public void eliminarServicioAdicional(ServicioAdicional servicio) {
        serviciosAdicionales.remove(servicio);
    }
}