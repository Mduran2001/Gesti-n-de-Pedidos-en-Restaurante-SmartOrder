/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Dao.UsuarioDao;
import Model.Usuario;

/**
 *
 * @author casti
 */
public class LoginService {

    private final UsuarioDao usuarioDao;

    public LoginService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

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
