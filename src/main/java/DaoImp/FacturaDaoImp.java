/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.FacturaDao;
import Model.Factura;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class FacturaDaoImp implements FacturaDao {

    private static final String TABLA = "factura";

    private Factura convertir(ResultSet rs) throws SQLException {
        Factura item = new Factura();
        item.setIdFactura(rs.getInt("id_factura"));
        item.setIdPago(rs.getInt("id_pago"));
        item.setIdPedido(rs.getInt("id_pedido"));
        item.setSubtotal(rs.getDouble("subtotal"));
        item.setImpuestos(rs.getDouble("impuestos"));
        item.setTotal(rs.getDouble("total"));
        item.setFechaEmision((rs.getTimestamp("fecha_emision") == null ? null : rs.getTimestamp("fecha_emision").toLocalDateTime()));
        return item;
    }

    @Override
    public boolean insertar(Factura factura) {
        String sql = "INSERT INTO factura (id_pago, id_pedido, subtotal, impuestos, total, fecha_emision) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPago());
            ps.setInt(2, factura.getIdPedido());
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getImpuestos());
            ps.setDouble(5, factura.getTotal());
            ps.setTimestamp(6, factura.getFechaEmision() == null ? null : Timestamp.valueOf(factura.getFechaEmision()));
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar factura", ex);
        }
    }

    @Override
    public Factura buscarPorId(int idFactura) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en factura", ex);
        }
    }

    @Override
    public List<Factura> listar() {
        String sql = "SELECT * FROM factura";
        List<Factura> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar factura", ex);
        }
    }

    @Override
    public Factura buscarPorPedido(int idPedido) {
        String sql = "SELECT * FROM factura WHERE id_pedido = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en buscarPorPedido", ex);
        }
    }

    @Override
    public boolean actualizar(Factura factura) {
        String sql = "UPDATE factura SET id_pago = ?, id_pedido = ?, subtotal = ?, impuestos = ?, total = ?, fecha_emision = ? WHERE id_factura = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPago());
            ps.setInt(2, factura.getIdPedido());
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getImpuestos());
            ps.setDouble(5, factura.getTotal());
            ps.setTimestamp(6, factura.getFechaEmision() == null ? null : Timestamp.valueOf(factura.getFechaEmision()));
            ps.setInt(7, factura.getIdFactura());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar factura", ex);
        }
    }

    @Override
    public boolean eliminar(int idFactura) {
        String sql = "DELETE FROM factura WHERE id_factura = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar factura", ex);
        }
    }
}
