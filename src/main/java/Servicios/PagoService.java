/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.PagoDao;
import Model.Pago;
import java.util.List;
/**
 *
 * @author casti
 */
public class PagoService {

    private PagoDao pagoDao;

    public PagoService(PagoDao pagoDao) {
        this.pagoDao = pagoDao;
    }

    public boolean registrarPago(Pago pago) {
        if (pago == null || pago.getMonto() <= 0) {
            return false;
        }

        return pagoDao.insertar(pago);
    }

    public Pago buscarPago(int idPago) {
        if (idPago <= 0) {
            return null;
        }

        return pagoDao.buscarPorId(idPago);
    }

    public List<Pago> listarPagos() {
        return pagoDao.listar();
    }

    public List<Pago> listarPagosPorPedido(int idPedido) {
        if (idPedido <= 0) {
            return null;
        }

        return pagoDao.listarPorPedido(idPedido);
    }

    public boolean actualizarPago(Pago pago) {
        if (pago == null || pago.getIdPago() <= 0
                || pago.getMonto() <= 0) {
            return false;
        }

        return pagoDao.actualizar(pago);
    }

    public boolean eliminarPago(int idPago) {
        if (idPago <= 0) {
            return false;
        }

        return pagoDao.eliminar(idPago);
    }
}