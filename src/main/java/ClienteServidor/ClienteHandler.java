/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClienteServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mario
 */
public class ClienteHandler implements Runnable {

    private final Socket socket;
    private final GestorConexiones gestor;

    public ClienteHandler(Socket socket, GestorConexiones gestor) {
        this.socket = socket;
        this.gestor = gestor;
    }

    @Override
    public void run() {
        try (Socket conexion = socket; BufferedReader entrada = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8)); PrintWriter salida = new PrintWriter(conexion.getOutputStream(), true, StandardCharsets.UTF_8)) {

            conexion.setSoTimeout(30000);
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.length() > 1024) {
                    salida.println("ERROR: Mensaje demasiado largo");
                    break;
                }
                switch (mensaje.trim().toUpperCase(java.util.Locale.ROOT)) {
                    case "PING":
                        salida.println("PONG");
                        break;
                    case "SALIR":
                        salida.println("ADIOS");
                        return;
                    default:
                        salida.println("ERROR: Comando no reconocido");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Cliente desconectado: " + e.getMessage());
        } finally {
            gestor.retirar(socket);
        }
    }
}
