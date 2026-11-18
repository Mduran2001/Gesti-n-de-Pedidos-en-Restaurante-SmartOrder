package Controladores;

import Model.Usuario;
import Servicios.LoginService;
import java.util.Objects;

public class LoginController {

    private final LoginService servicio;

    public LoginController(LoginService servicio) {
        this.servicio = Objects.requireNonNull(servicio);
    }

    /** @return el usuario autenticado o null si las credenciales no son válidas. */
    public Usuario iniciarSesion(String correo, String contrasena) {
        return servicio.verificarCredenciales(correo, contrasena);
    }
}
