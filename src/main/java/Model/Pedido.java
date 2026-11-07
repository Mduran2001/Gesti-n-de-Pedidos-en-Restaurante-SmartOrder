/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDateTime;
/**
 *
 * @author casti
 */
public class Pedido {

    private int idPedido;
    private int idCliente;
    private int idUsuario;
    private int idMesa;
    private String estado;
    private double total;
    private LocalDateTime fechaPedido;

    public Pedido() {
    }

    public Pedido(int idPedido, int idCliente, int idUsuario,
            int idMesa, String estado, double total,
            LocalDateTime fechaPedido) {

        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idMesa = idMesa;
        this.estado = estado;
        this.total = total;
        this.fechaPedido = fechaPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
}