/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Model.Producto;
import Servicios.ProductoService;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class ProductoController {

    private final ProductoService servicio;

    public ProductoController(ProductoService servicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio");
    }

    public boolean registrarProducto(Producto producto) {
        return servicio.registrarProducto(producto);
    }

    public Producto buscarProducto(int idProducto) {
        return servicio.buscarProducto(idProducto);
    }

    public List<Producto> listarProductos() {
        return servicio.listarProductos();
    }

    public List<Producto> filtrarPorCategoria(int idCategoria) {
        return servicio.listarProductosPorCategoria(idCategoria);
    }

    public boolean actualizarProducto(Producto producto) {
        return servicio.actualizarProducto(producto);
    }

    public boolean eliminarProducto(int idProducto) {
        return servicio.eliminarProducto(idProducto);
    }
}
