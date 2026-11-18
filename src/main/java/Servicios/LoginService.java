package Servicios;

import Dao.UsuarioDao;
import Model.Usuario;

/** Verifica las credenciales contra la base de datos. */
public class LoginService {

    private final UsuarioDao usuarioDao;

    public LoginService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    /** @return el usuario si las credenciales son correctas y está activo; null si no. */
    public Usuario verificarCredenciales(String correo, String contrasena) {
        if (correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioDao.buscarPorCorreo(correo.trim());

        if (usuario != null
                && contrasena.equals(usuario.getContrasena())){
            return usuario;
        }
        return null;
    }
}
