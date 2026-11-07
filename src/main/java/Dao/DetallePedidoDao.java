/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.DetallePedido;
import java.util.List;
/**
 *
 * @author casti
 */
public interface DetallePedidoDao {

    boolean insertar(DetallePedido detallePedido);

    DetallePedido buscarPorId(int idDetallePedido);

    List<DetallePedido> listar();

    List<DetallePedido> listarPorPedido(int idPedido);

    boolean actualizar(DetallePedido detallePedido);

    boolean eliminar(int idDetallePedido);
}