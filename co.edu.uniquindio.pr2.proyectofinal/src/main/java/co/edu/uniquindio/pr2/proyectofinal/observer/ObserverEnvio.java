package co.edu.uniquindio.pr2.proyectofinal.observer;

import co.edu.uniquindio.pr2.proyectofinal.model.Envio;
import co.edu.uniquindio.pr2.proyectofinal.model.EstadoEnvio;

public interface ObserverEnvio {
    void actualizar(Envio envio, EstadoEnvio nuevoEstado);
}