package co.edu.uniquindio.pr2.proyectofinal.decorator;

public abstract class EnvioDecorator implements IEnvioDecorator {
    protected IEnvioDecorator envioDecorado;

    public EnvioDecorator(IEnvioDecorator envioDecorado) {
        this.envioDecorado = envioDecorado;
    }

    public double calcularCosto() {
        return envioDecorado.calcularCosto();
    }

    public String descripcion() {
        return envioDecorado.descripcion();
    }
}