package co.edu.uniquindio.pr2.proyectofinal.factoryMethod.factory;

import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.model.IRepartidorBase;
import co.edu.uniquindio.pr2.proyectofinal.factoryMethod.model.RepartidorMoto;

public class MotoRepartidorFactory extends RepartidorFactory {
    @Override
    public IRepartidorBase crearRepartidor() {
        return new RepartidorMoto();
    }
}