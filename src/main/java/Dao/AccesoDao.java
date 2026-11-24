/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.RegistroAcceso;
import java.util.List;
/**
 *
 * @author casti
 */
public interface AccesoDao {

    boolean insertar(RegistroAcceso acceso);

    List<RegistroAcceso> listar();

    List<RegistroAcceso> listarPorUsuario(int idUsuario);
}
