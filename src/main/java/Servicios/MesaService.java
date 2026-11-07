/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.MesaDao;
import Model.Mesa;
import java.util.List;

/**
 *
 * @author castti
 */
public class MesaService {

    private MesaDao mesaDao;

    public MesaService(MesaDao mesaDao) {
        this.mesaDao = mesaDao;
    }

    public boolean registrarMesa(Mesa mesa) {
        if (mesa == null || mesa.getNumero() <= 0
                || mesa.getCapacidad() <= 0) {
            return false;
        }

        return mesaDao.insertar(mesa);
    }

    public Mesa buscarMesa(int idMesa) {
        if (idMesa <= 0) {
            return null;
        }

        return mesaDao.buscarPorId(idMesa);
    }

    public List<Mesa> listarMesas() {
        return mesaDao.listar();
    }

    public boolean actualizarMesa(Mesa mesa) {
        if (mesa == null || mesa.getIdMesa() <= 0) {
            return false;
        }

        return mesaDao.actualizar(mesa);
    }

    public boolean eliminarMesa(int idMesa) {
        if (idMesa <= 0) {
            return false;
        }

        return mesaDao.eliminar(idMesa);
    }
}