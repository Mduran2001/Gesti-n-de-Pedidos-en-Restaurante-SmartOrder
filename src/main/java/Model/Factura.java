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
public class Factura {

    private int idFactura;
    private int idPago;
    private int idPedido;
    private double subtotal;
    private double impuestos;
    private double total;
    private LocalDateTime fechaEmision;

    public Factura() {
    }

    public Factura(int idFactura, int idPago, int idPedido,
            double subtotal, double impuestos, double total,
            LocalDateTime fechaEmision) {

        this.idFactura = idFactura;
        this.idPago = idPago;
        this.idPedido = idPedido;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.total = total;
        this.fechaEmision = fechaEmision;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
}