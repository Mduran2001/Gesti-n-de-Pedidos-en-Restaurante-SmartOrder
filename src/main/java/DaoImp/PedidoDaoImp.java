/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.PedidoDao;
import Model.Pedido;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class PedidoDaoImp implements PedidoDao {

    private static final String TABLA = "pedido";

    private Pedido convertir(ResultSet rs) throws SQLException {
        Pedido item = new Pedido();
        item.setIdPedido(rs.getInt("id_pedido"));
        item.setIdCliente(rs.getInt("id_cliente"));
        item.setIdUsuario(rs.getInt("id_usuario"));
        item.setIdMesa(rs.getInt("id_mesa"));
        item.setEstado(rs.getString("estado"));
        item.setTotal(rs.getDouble("total"));
        item.setFechaPedido((rs.getTimestamp("fecha_pedido") == null ? null : rs.getTimestamp("fecha_pedido").toLocalDateTime()));
        return item;
    }

    @Override
    public boolean insertar(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_cliente, id_usuario, id_mesa, estado, total, fecha_pedido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdUsuario());
            ps.setInt(3, pedido.getIdMesa());
            ps.setString(4, pedido.getEstado());
            ps.setDouble(5, pedido.getTotal());
            ps.setTimestamp(6, pedido.getFechaPedido() == null ? null : Timestamp.valueOf(pedido.getFechaPedido()));
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar pedido", ex);
        }
    }

    @Override
    public Pedido buscarPorId(int idPedido) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en pedido", ex);
        }
    }

    @Override
    public List<Pedido> listar() {
        String sql = "SELECT * FROM pedido";
        List<Pedido> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar pedido", ex);
        }
    }

    @Override
    public List<Pedido> listarPorCliente(int idCliente) {
        String sql = "SELECT * FROM pedido WHERE id_cliente = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                List<Pedido> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(convertir(rs));
                }
                return resultados;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en listarPorCliente", ex);
        }
    }

    @Override
    public List<Pedido> listarPorEstado(String estado) {
        String sql = "SELECT * FROM pedido WHERE estado = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                List<Pedido> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(convertir(rs));
                }
                return resultados;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en listarPorEstado", ex);
        }
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET id_cliente = ?, id_usuario = ?, id_mesa = ?, estado = ?, total = ?, fecha_pedido = ? WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdUsuario());
            ps.setInt(3, pedido.getIdMesa());
            ps.setString(4, pedido.getEstado());
            ps.setDouble(5, pedido.getTotal());
            ps.setTimestamp(6, pedido.getFechaPedido() == null ? null : Timestamp.valueOf(pedido.getFechaPedido()));
            ps.setInt(7, pedido.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar pedido", ex);
        }
    }

    @Override
    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar pedido", ex);
        }
    }
}
