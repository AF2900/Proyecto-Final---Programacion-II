package co.edu.uniquindio.pr2.proyectofinal.model;

public class Tarifa {
    private String idTarifa;
    private double precio;

    public Tarifa(String idTarifa, double precio) {
        this.idTarifa = idTarifa;
        this.precio = precio;
    }

    public String getIdTarifa() {return idTarifa;}

    public void setIdTarifa(String idTarifa) {this.idTarifa = idTarifa;}

    public double getPrecio() {return precio;}

    public void setPrecio(double precio) {this.precio = precio;}

    @Override
    public String toString() {
        return "idTarifa=" + idTarifa + ", precio=" + precio;
    }
}
