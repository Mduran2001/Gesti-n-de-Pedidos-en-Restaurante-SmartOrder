/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DaoImp;

import Configuracion.ConexionDB;
import Dao.AccesoDao;
import Model.RegistroAcceso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class AccesoDaoImp implements AccesoDao {

    private RegistroAcceso convertir(ResultSet rs) throws SQLException {
        RegistroAcceso item = new RegistroAcceso();
        item.setIdAcceso(rs.getInt("id_acceso"));
        item.setIdUsuario(rs.getInt("id_usuario"));
        Timestamp ts = rs.getTimestamp("fecha_hora");
        item.setFechaHora(ts != null ? ts.toLocalDateTime() : null);
        return item;
    }

    @Override
    public boolean insertar(RegistroAcceso acceso) {
        // fecha_hora se llena sola con CURRENT_TIMESTAMP
        String sql = "INSERT INTO registro_acceso (id_usuario) VALUES (?)";
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, acceso.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al registrar el acceso", ex);
        }
    }

    @Override
    public List<RegistroAcceso> listar() {
        String sql = "SELECT * FROM registro_acceso ORDER BY fecha_hora DESC";
        List<RegistroAcceso> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(convertir(rs));
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar accesos", ex);
        }
    }

    @Override
    public List<RegistroAcceso> listarPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM registro_acceso WHERE id_usuario = ? ORDER BY fecha_hora DESC";
        List<RegistroAcceso> resultados = new ArrayList<>();
        try (Connection cn = ConexionDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(convertir(rs));
                }
            }
            return resultados;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al listar accesos del usuario", ex);
        }
    }
}
