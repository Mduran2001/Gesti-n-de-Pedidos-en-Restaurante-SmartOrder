package Dao;

import Model.RegistroAcceso;
import java.util.List;

public interface AccesoDao {

    boolean insertar(RegistroAcceso acceso);

    List<RegistroAcceso> listar();

    List<RegistroAcceso> listarPorUsuario(int idUsuario);
}
