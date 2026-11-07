/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Pedido;
import java.util.List;
/**
 *
 * @author casti
 */
public interface PedidoDao {

    boolean insertar(Pedido pedido);

    Pedido buscarPorId(int idPedido);

    List<Pedido> listar();

    List<Pedido> listarPorCliente(int idCliente);

    List<Pedido> listarPorEstado(String estado);

    boolean actualizar(Pedido pedido);

    boolean eliminar(int idPedido);
}