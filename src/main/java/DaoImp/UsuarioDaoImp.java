/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.UsuarioDao;
import Model.Usuario;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class UsuarioDaoImp implements UsuarioDao {

    private static final String TABLA = "usuario";

    private Usuario convertir(ResultSet rs) throws SQLException {
        Usuario item = new Usuario();
        item.setIdUsuario(rs.getInt("id_usuario"));
        item.setIdRol(rs.getInt("id_rol"));
        item.setNombre(rs.getString("nombre"));
        item.setApellido(rs.getString("apellido"));
        item.setCorreo(rs.getString("correo"));
        item.setContrasena(rs.getString("contrasena"));
        item.setEstado(rs.getString("estado"));
        return item;
    }

    @Override
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuario (id_rol, nombre, apellido, correo, contrasena, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdRol());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getContrasena());
            ps.setString(6, usuario.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar usuario", ex);
        }
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en usuario", ex);
        }
    }

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar usuario", ex);
        }
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en buscarPorCorreo", ex);
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET id_rol = ?, nombre = ?, apellido = ?, correo = ?, contrasena = ?, estado = ? WHERE id_usuario = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdRol());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getContrasena());
            ps.setString(6, usuario.getEstado());
            ps.setInt(7, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar usuario", ex);
        }
    }

    @Override
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar usuario", ex);
        }
    }
}
