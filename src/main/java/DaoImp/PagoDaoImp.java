/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.PagoDao;
import Model.Pago;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class PagoDaoImp implements PagoDao {

    private static final String TABLA = "pago";

    private Pago convertir(ResultSet rs) throws SQLException {
        Pago item = new Pago();
        item.setIdPago(rs.getInt("id_pago"));
        item.setIdPedido(rs.getInt("id_pedido"));
        item.setMetodoPago(rs.getString("metodo_pago"));
        item.setMonto(rs.getDouble("monto"));
        item.setFechaPago((rs.getTimestamp("fecha_pago") == null ? null : rs.getTimestamp("fecha_pago").toLocalDateTime()));
        item.setEstado(rs.getString("estado"));
        return item;
    }

   
    @Override
    public boolean insertar(Pago pago) {
        String sql = "INSERT INTO pago (id_pedido, metodo_pago, monto, fecha_pago, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pago.getIdPedido());
            ps.setString(2, pago.getMetodoPago());
            ps.setDouble(3, pago.getMonto());
            ps.setTimestamp(4, pago.getFechaPago() == null ? null : Timestamp.valueOf(pago.getFechaPago()));
            ps.setString(5, pago.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar pago", ex);
        }
    }

    @Override
    public Pago buscarPorId(int idPago) {
        String sql = "SELECT * FROM pago WHERE id_pago = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPago);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en pago", ex);
        }
    }

    @Override
    public List<Pago> listar() {
        String sql = "SELECT * FROM pago";
        List<Pago> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar pago", ex);
        }
    }

    @Override
    public List<Pago> listarPorPedido(int idPedido) {
        String sql = "SELECT * FROM pago WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                List<Pago> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(convertir(rs));
                }
                return resultados;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en listarPorPedido", ex);
        }
    }

    @Override
    public boolean actualizar(Pago pago) {
        String sql = "UPDATE pago SET id_pedido = ?, metodo_pago = ?, monto = ?, fecha_pago = ?, estado = ? WHERE id_pago = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pago.getIdPedido());
            ps.setString(2, pago.getMetodoPago());
            ps.setDouble(3, pago.getMonto());
            ps.setTimestamp(4, pago.getFechaPago() == null ? null : Timestamp.valueOf(pago.getFechaPago()));
            ps.setString(5, pago.getEstado());
            ps.setInt(6, pago.getIdPago());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar pago", ex);
        }
    }

    @Override
    public boolean eliminar(int idPago) {
        String sql = "DELETE FROM pago WHERE id_pago = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPago);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar pago", ex);
        }
    }
}
