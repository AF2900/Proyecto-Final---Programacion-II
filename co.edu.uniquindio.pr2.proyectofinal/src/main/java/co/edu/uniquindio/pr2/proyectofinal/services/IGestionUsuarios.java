package co.edu.uniquindio.pr2.proyectofinal.services;

import co.edu.uniquindio.pr2.proyectofinal.model.Usuario;
import co.edu.uniquindio.pr2.proyectofinal.model.Direccion;

public interface IGestionUsuarios {
    void agregarDireccionAUsuario(Usuario usuario, Direccion direccion);
    void eliminarDireccionDeUsuario(Usuario usuario, Direccion direccion);
}