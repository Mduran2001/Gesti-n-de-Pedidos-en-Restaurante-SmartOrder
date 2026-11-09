/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.Usuario;
import Servicios.AutenticacionService;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class AutenticacionController {
    private final AutenticacionService servicio;
    public AutenticacionController(AutenticacionService servicio) {
        this.servicio = Objects.requireNonNull(servicio);
    }
    public Usuario iniciarSesion(String correo, String contrasena) {
        return servicio.iniciarSesion(correo, contrasena);
    }
}
