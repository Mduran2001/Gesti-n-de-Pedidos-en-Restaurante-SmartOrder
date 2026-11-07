/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.UsuarioDao;
import Model.Usuario;
/**
 *
 * @author mario
 */
public class AutenticacionService {

    private UsuarioDao usuarioDao;

    public AutenticacionService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Usuario iniciarSesion(String correo, String contrasena) {

        if (correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioDao.buscarPorCorreo(correo);

        if (usuario != null
                && usuario.getContrasena().equals(contrasena)
                && "Activo".equalsIgnoreCase(usuario.getEstado())) {
            return usuario;
        }

        return null;
    }
}