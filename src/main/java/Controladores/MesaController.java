/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.Mesa;
import Servicios.MesaService;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class MesaController {

    private final MesaService servicio;

    public MesaController(MesaService servicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio");
    }

    public boolean registrarMesa(Mesa mesa) {
        return servicio.registrarMesa(mesa);
    }

    public Mesa buscarMesa(int idMesa) {
        return servicio.buscarMesa(idMesa);
    }

    public List<Mesa> listarMesas() {
        return servicio.listarMesas();
    }

    public boolean actualizarMesa(Mesa mesa) {
        return servicio.actualizarMesa(mesa);
    }

    public boolean eliminarMesa(int idMesa) {
        return servicio.eliminarMesa(idMesa);
    }
}
