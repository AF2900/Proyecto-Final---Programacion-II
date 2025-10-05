package co.edu.uniquindio.pr2.proyectofinal.builder;

import co.edu.uniquindio.pr2.proyectofinal.model.Tarifa;

public class TarifaBuilder {

    private String idTarifa;
    private double precio;

    public TarifaBuilder idTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
        return this;
    }

    public TarifaBuilder precio(double precio) {
        this.precio = precio;
        return this;
    }

    public Tarifa build() {
        return new Tarifa(idTarifa, precio);
    }
}