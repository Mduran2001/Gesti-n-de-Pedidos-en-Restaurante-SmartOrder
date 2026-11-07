/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.Factura;
import java.util.List;
/**
 *
 * @author casti
 */
public interface FacturaDao {

    boolean insertar(Factura factura);

    Factura buscarPorId(int idFactura);

    List<Factura> listar();

    Factura buscarPorPedido(int idPedido);

    boolean actualizar(Factura factura);

    boolean eliminar(int idFactura);
}