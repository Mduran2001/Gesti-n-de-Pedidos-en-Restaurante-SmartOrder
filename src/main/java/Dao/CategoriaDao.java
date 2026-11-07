/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Categoria;
import java.util.List;
/**
 *
 * @author casti
 */
public interface CategoriaDao {

    boolean insertar(Categoria categoria);

    Categoria buscarPorId(int idCategoria);

    List<Categoria> listar();

    boolean actualizar(Categoria categoria);

    boolean eliminar(int idCategoria);
}