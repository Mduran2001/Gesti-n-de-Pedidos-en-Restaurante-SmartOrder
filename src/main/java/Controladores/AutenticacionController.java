/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.RegistroAcceso;
import Model.Usuario;
import Servicios.AutenticacionService;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Usuario
 */
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
