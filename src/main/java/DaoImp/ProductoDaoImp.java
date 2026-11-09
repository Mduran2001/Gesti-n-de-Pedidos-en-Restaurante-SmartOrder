/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.ProductoDao;
import Model.Producto;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class ProductoDaoImp implements ProductoDao {

    private static final String TABLA = "producto";

    private Producto convertir(ResultSet rs) throws SQLException {
        Producto item = new Producto();
        item.setIdProducto(rs.getInt("id_producto"));
        item.setIdCategoria(rs.getInt("id_categoria"));
        item.setNombre(rs.getString("nombre"));
        item.setDescripcion(rs.getString("descripcion"));
        item.setPrecio(rs.getDouble("precio"));
        item.setEstado(rs.getString("estado"));
        return item;
    }

    @Override
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO producto (id_categoria, nombre, descripcion, precio, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar producto", ex);
        }
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en producto", ex);
        }
    }

    @Override
    public List<Producto> listar() {
        String sql = "SELECT * FROM producto";
        List<Producto> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar producto", ex);
        }
    }

    @Override
    public List<Producto> listarPorCategoria(int idCategoria) {
        String sql = "SELECT * FROM producto WHERE id_categoria = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                List<Producto> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(convertir(rs));
                }
                return resultados;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en listarPorCategoria", ex);
        }
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET id_categoria = ?, nombre = ?, descripcion = ?, precio = ?, estado = ? WHERE id_producto = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getEstado());
            ps.setInt(6, producto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar producto", ex);
        }
    }

    @Override
    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar producto", ex);
        }
    }
}
