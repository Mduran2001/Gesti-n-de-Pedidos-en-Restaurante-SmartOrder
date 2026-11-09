/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Dao.MesaDao;
import Model.Mesa;
import Configuracion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class MesaDaoImp implements MesaDao {

    private static final String TABLA = "mesa";

    private Mesa convertir(ResultSet rs) throws SQLException {
        Mesa item = new Mesa();
        item.setIdMesa(rs.getInt("id_mesa"));
        item.setNumero(rs.getInt("numero"));
        item.setCapacidad(rs.getInt("capacidad"));
        item.setEstado(rs.getString("estado"));
        return item;
    }

    @Override
    public boolean insertar(Mesa mesa) {
        String sql = "INSERT INTO mesa (numero, capacidad, estado) VALUES (?, ?, ?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al insertar mesa", ex);
        }
    }

    @Override
    public Mesa buscarPorId(int idMesa) {
        String sql = "SELECT * FROM mesa WHERE id_mesa = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al buscar en mesa", ex);
        }
    }

    @Override
    public List<Mesa> listar() {
        String sql = "SELECT * FROM mesa";
        List<Mesa> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar mesa", ex);
        }
    }

    @Override
    public boolean actualizar(Mesa mesa) {
        String sql = "UPDATE mesa SET numero = ?, capacidad = ?, estado = ? WHERE id_mesa = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());
            ps.setInt(4, mesa.getIdMesa());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al actualizar mesa", ex);
        }
    }

    @Override
    public boolean eliminar(int idMesa) {
        String sql = "DELETE FROM mesa WHERE id_mesa = ?";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al eliminar mesa", ex);
        }
    }
}
