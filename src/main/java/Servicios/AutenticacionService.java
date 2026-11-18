package Servicios;

import Dao.AccesoDao;
import Model.RegistroAcceso;
import Model.Usuario;
import java.util.List;

/** Lleva el registro de quiénes han ingresado al sistema. */
public class AutenticacionService {

    private final AccesoDao accesoDao;

    public AutenticacionService(AccesoDao accesoDao) {
        this.accesoDao = accesoDao;
    }

    public void registrarIngreso(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no válido.");
        }
        RegistroAcceso acceso = new RegistroAcceso();
        acceso.setIdUsuario(usuario.getIdUsuario());
        accesoDao.insertar(acceso);
    }

    public List<RegistroAcceso> listarAccesos() {
        return accesoDao.listar();
    }

    public List<RegistroAcceso> listarAccesosDeUsuario(int idUsuario) {
        return accesoDao.listarPorUsuario(idUsuario);
    }
}
