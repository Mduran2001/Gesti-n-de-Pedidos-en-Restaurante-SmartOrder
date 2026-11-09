/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.Pedido;
import Servicios.PedidoService;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class PedidoController {

    private final PedidoService servicio;

    public PedidoController(PedidoService servicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio");
    }

    public boolean crearPedido(Pedido pedido) {
        return servicio.registrarPedido(pedido);
    }

    public Pedido buscarPedido(int idPedido) {
        return servicio.buscarPedido(idPedido);
    }

    public List<Pedido> listarPedidos() {
        return servicio.listarPedidos();
    }

    public List<Pedido> listarPorCliente(int idCliente) {
        return servicio.listarPedidosPorCliente(idCliente);
    }

    public List<Pedido> listarPorEstado(String estado) {
        return servicio.listarPedidosPorEstado(estado);
    }

    public boolean actualizarPedido(Pedido pedido) {
        return servicio.actualizarPedido(pedido);
    }

    public boolean eliminarPedido(int idPedido) {
        return servicio.eliminarPedido(idPedido);
    }
}
