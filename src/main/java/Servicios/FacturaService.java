/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.FacturaDao;
import Model.Factura;
import java.util.List;
/**
 *
 * @author casti
 */
public class FacturaService {

    private FacturaDao facturaDao;

    public FacturaService(FacturaDao facturaDao) {
        this.facturaDao = facturaDao;
    }

    public boolean registrarFactura(Factura factura) {
        if (factura == null) {
            return false;
        }

        if (factura.getSubtotal() < 0
                || factura.getImpuestos() < 0
                || factura.getTotal() < 0) {
            return false;
        }

        return facturaDao.insertar(factura);
    }

    public Factura buscarFactura(int idFactura) {
        if (idFactura <= 0) {
            return null;
        }

        return facturaDao.buscarPorId(idFactura);
    }

    public List<Factura> listarFacturas() {
        return facturaDao.listar();
    }

    public Factura buscarPorPedido(int idPedido) {
        if (idPedido <= 0) {
            return null;
        }

        return facturaDao.buscarPorPedido(idPedido);
    }

    public boolean actualizarFactura(Factura factura) {
        if (factura == null || factura.getIdFactura() <= 0) {
            return false;
        }

        return facturaDao.actualizar(factura);
    }

    public boolean eliminarFactura(int idFactura) {
        if (idFactura <= 0) {
            return false;
        }

        return facturaDao.eliminar(idFactura);
    }
}