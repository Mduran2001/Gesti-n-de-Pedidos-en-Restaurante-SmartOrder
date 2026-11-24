/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class ConexionDB {

    private static final String URL =
        "jdbc:mysql://localhost:3306/SmartOrder"
        + "?useSSL=false"
        + "&serverTimezone=America/Costa_Rica";

    private static final String USUARIO = "root";

    private static final String PASSWORD = "TuClaveSegura#2026!";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
            URL,
            USUARIO,
            PASSWORD
        );
    }
}