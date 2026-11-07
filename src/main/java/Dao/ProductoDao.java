/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Producto;
import java.util.List;

/**
 *
 * @author casti
 */
public interface ProductoDao {

    boolean insertar(Producto producto);

    Producto buscarPorId(int idProducto);

    List<Producto> listar();

    List<Producto> listarPorCategoria(int idCategoria);

    boolean actualizar(Producto producto);

    boolean eliminar(int idProducto);
}