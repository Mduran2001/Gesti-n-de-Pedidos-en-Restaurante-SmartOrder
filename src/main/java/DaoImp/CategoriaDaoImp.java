/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.CategoriaDao;
import Model.Categoria;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class CategoriaDaoImp implements CategoriaDao {

    private static final String TABLA = "categoria";

    private Categoria convertir(ResultSet rs) throws SQLException {
        Categoria item = new Categoria();
        item.setIdCategoria(rs.getInt("id_categoria"));
        item.setNombre(rs.getString("nombre"));
        item.setDescripcion(rs.getString("descripcion"));
        return item;

    }

    @Override
    public boolean insertar(Categoria categoria) {
        String sql = "Insertar Into categoria (nombre, descripcion) VALUES (?,?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar categoria", ex);
        }
    }

    @Override
    public Categoria buscarPorId(int idCategoria) {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en categoria", ex);
        }
    }

    @Override
    public List<Categoria> listar() {
        String sql = "SELECT * FROM categoria";
        List<Categoria> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar categoria", ex);
        }
    }

    @Override
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id_categoria = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getIdCategoria());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar categoria", ex);
        }
    }

    @Override
    public boolean eliminar(int idCategoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar categoria", ex);
        }
    }
}
