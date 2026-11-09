/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.Factura;
import Servicios.FacturaService;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class FacturaController {

    private final FacturaService servicio;

    public FacturaController(FacturaService servicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio");
    }

    public boolean registrarFactura(Factura factura) {
        return servicio.registrarFactura(factura);
    }

    public Factura buscarFactura(int idFactura) {
        return servicio.buscarFactura(idFactura);
    }

    public Factura buscarPorPedido(int idPedido) {
        return servicio.buscarPorPedido(idPedido);
    }

    public List<Factura> listarFacturas() {
        return servicio.listarFacturas();
    }

    public boolean actualizarFactura(Factura factura) {
        return servicio.actualizarFactura(factura);
    }

    public boolean eliminarFactura(int idFactura) {
        return servicio.eliminarFactura(idFactura);
    }
}
