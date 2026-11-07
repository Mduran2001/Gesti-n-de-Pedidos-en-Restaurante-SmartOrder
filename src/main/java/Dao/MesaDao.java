/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Mesa;
import java.util.List;
/**
 *
 * @author casti
 */
public interface MesaDao {

    boolean insertar(Mesa mesa);

    Mesa buscarPorId(int idMesa);

    List<Mesa> listar();

    boolean actualizar(Mesa mesa);

    boolean eliminar(int idMesa);
}