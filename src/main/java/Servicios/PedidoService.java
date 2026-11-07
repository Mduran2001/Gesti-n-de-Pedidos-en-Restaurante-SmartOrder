/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.PedidoDao;
import Model.Pedido;
import java.util.List;
/**
 *
 * @author casti
 */
public class PedidoService {

    private PedidoDao pedidoDao;

    public PedidoService(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public boolean registrarPedido(Pedido pedido) {
        if (pedido == null
                || pedido.getIdCliente() <= 0
                || pedido.getTotal() < 0) {
            return false;
        }

        return pedidoDao.insertar(pedido);
    }

    public Pedido buscarPedido(int idPedido) {
        if (idPedido <= 0) {
            return null;
        }

        return pedidoDao.buscarPorId(idPedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDao.listar();
    }

    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        if (idCliente <= 0) {
            return null;
        }

        return pedidoDao.listarPorCliente(idCliente);
    }

    public List<Pedido> listarPedidosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return null;
        }

        return pedidoDao.listarPorEstado(estado);
    }

    public boolean actualizarPedido(Pedido pedido) {
        if (pedido == null || pedido.getIdPedido() <= 0) {
            return false;
        }

        return pedidoDao.actualizar(pedido);
    }

    public boolean eliminarPedido(int idPedido) {
        if (idPedido <= 0) {
            return false;
        }

        return pedidoDao.eliminar(idPedido);
    }
}