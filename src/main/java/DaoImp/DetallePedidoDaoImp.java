/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.DetallePedidoDao;
import Model.DetallePedido;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class DetallePedidoDaoImp implements DetallePedidoDao {

    private static final String TABLA = "detalle_pedido";

    private DetallePedido convertir(ResultSet rs) throws SQLException {
        DetallePedido item = new DetallePedido();
        item.setIdDetallePedido(rs.getInt("id_detalle_pedido"));
        item.setIdPedido(rs.getInt("id_pedido"));
        item.setIdProducto(rs.getInt("id_producto"));
        item.setCantidad(rs.getInt("cantidad"));
        item.setObservaciones(rs.getString("observaciones"));
        return item;
    }

    @Override
    public boolean insertar(DetallePedido detallePedido) {
        String sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, observaciones) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, detallePedido.getIdPedido());
        ps.setInt(2, detallePedido.getIdProducto());
        ps.setInt(3, detallePedido.getCantidad());
        ps.setString(4, detallePedido.getObservaciones());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar detalle_pedido", ex);
        }
    }

    @Override
    public DetallePedido buscarPorId(int idDetallePedido) {
        String sql = "SELECT * FROM detalle_pedido WHERE id_detalle_pedido = ?";
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idDetallePedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en detalle_pedido", ex);
        }
    }

    @Override
    public List<DetallePedido> listar() {
        String sql = "SELECT * FROM detalle_pedido";
        List<DetallePedido> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar detalle_pedido", ex);
        }
    }

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {
        String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                List<DetallePedido> resultados = new ArrayList<>();
                while (rs.next()) resultados.add(convertir(rs));
                return resultados;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en listarPorPedido", ex);
        }
    }

    @Override
    public boolean actualizar(DetallePedido detallePedido) {
        String sql = "UPDATE detalle_pedido SET id_pedido = ?, id_producto = ?, cantidad = ?, observaciones = ? WHERE id_detalle_pedido = ?";
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, detallePedido.getIdPedido());
        ps.setInt(2, detallePedido.getIdProducto());
        ps.setInt(3, detallePedido.getCantidad());
        ps.setString(4, detallePedido.getObservaciones());
        ps.setInt(5, detallePedido.getIdDetallePedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar detalle_pedido", ex);
        }
    }

    @Override
    public boolean eliminar(int idDetallePedido) {
        String sql = "DELETE FROM detalle_pedido WHERE id_detalle_pedido = ?";
        try (Connection cn = ConexionDB.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idDetallePedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar detalle_pedido", ex);
        }
    }
}