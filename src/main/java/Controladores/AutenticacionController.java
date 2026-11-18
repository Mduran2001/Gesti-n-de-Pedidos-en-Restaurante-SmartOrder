package Controladores;

import Model.RegistroAcceso;
import Model.Usuario;
import Servicios.AutenticacionService;
import java.util.List;
import java.util.Objects;

public class AutenticacionController {

    private final AutenticacionService servicio;

    public AutenticacionController(AutenticacionService servicio) {
        this.servicio = Objects.requireNonNull(servicio);
    }

    public void registrarIngreso(Usuario usuario) {
        servicio.registrarIngreso(usuario);
    }

    public List<RegistroAcceso> listarAccesos() {
        return servicio.listarAccesos();
    }

    public List<RegistroAcceso> listarAccesosDeUsuario(int idUsuario) {
        return servicio.listarAccesosDeUsuario(idUsuario);
    }
}
