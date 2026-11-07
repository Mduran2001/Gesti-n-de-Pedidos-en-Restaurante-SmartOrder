/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Pago;
import java.util.List;
/**
 *
 * @author casti
 */
public interface PagoDao {

    boolean insertar(Pago pago);

    Pago buscarPorId(int idPago);

    List<Pago> listar();

    List<Pago> listarPorPedido(int idPedido);

    boolean actualizar(Pago pago);

    boolean eliminar(int idPago);
}