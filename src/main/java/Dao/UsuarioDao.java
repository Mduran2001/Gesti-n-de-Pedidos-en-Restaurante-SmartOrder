/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Usuario;
import java.util.List;
/**
 *
 * @author casti
 */
public interface UsuarioDao {

    boolean insertar(Usuario usuario);

    Usuario buscarPorId(int idUsuario);

    Usuario buscarPorCorreo(String correo);

    List<Usuario> listar();

    boolean actualizar(Usuario usuario);

    boolean eliminar(int idUsuario);
}