/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantesmartorder.configuracion;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class PruebaConexionDB {
    public static void main(String[]args){
        try(Connection conexion = ConexionDB.conectar()){
            if(conexion!= null && !conexion.isClosed()){
                System.out.println("Conexion exitosa con SmartOrder");
            }
        }catch(SQLException e){
        System.out.println("Error de conexion" + e.getMessage());
    }
    }
}
