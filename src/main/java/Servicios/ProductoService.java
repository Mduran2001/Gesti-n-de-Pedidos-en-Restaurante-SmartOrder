/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Dao.ProductoDao;
import Model.Producto;
import java.util.List;
/**
 *
 * @author casti
 */
public class ProductoService {

    private ProductoDao productoDao;

    public ProductoService(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public boolean registrarProducto(Producto producto) {
        if (producto == null
                || producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()
                || producto.getPrecio() < 0) {
            return false;
        }

        return productoDao.insertar(producto);
    }

    public Producto buscarProducto(int idProducto) {
        if (idProducto <= 0) {
            return null;
        }

        return productoDao.buscarPorId(idProducto);
    }

    public List<Producto> listarProductos() {
        return productoDao.listar();
    }

    public List<Producto> listarProductosPorCategoria(int idCategoria) {
        if (idCategoria <= 0) {
            return null;
        }

        return productoDao.listarPorCategoria(idCategoria);
    }

    public boolean actualizarProducto(Producto producto) {
        if (producto == null || producto.getIdProducto() <= 0) {
            return false;
        }

        return productoDao.actualizar(producto);
    }

    public boolean eliminarProducto(int idProducto) {
        if (idProducto <= 0) {
            return false;
        }

        return productoDao.eliminar(idProducto);
    }
}