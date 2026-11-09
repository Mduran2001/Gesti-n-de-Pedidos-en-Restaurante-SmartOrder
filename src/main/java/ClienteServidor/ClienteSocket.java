/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClienteServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mario
 */
public class ClienteSocket implements AutoCloseable {

    private final Socket socket;
    private final BufferedReader entrada;
    private final PrintWriter salida;

    public ClienteSocket(String host, int puerto) throws IOException {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, puerto), 5000);
            socket.setSoTimeout(10000);
            entrada = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
            salida = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            throw e;
        }
    }

    public String enviar(String mensaje) throws IOException {
        if (mensaje == null || mensaje.contains("\n") || mensaje.contains("\r") || mensaje.length() > 1024) {
            throw new IllegalArgumentException("Solicitud no válida");
        }
        salida.println(mensaje);
        if (salida.checkError()) {
            throw new IOException("No se pudo enviar la solicitud");
        }
        String respuesta = entrada.readLine();
        if (respuesta == null) {
            throw new IOException("El servidor cerró la conexión");
        }
        return respuesta;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        try (ClienteSocket cliente = new ClienteSocket("localhost", ServidorSocket.PUERTO)) {
            System.out.println("Respuesta: " + cliente.enviar("PING"));
            System.out.println("Respuesta: " + cliente.enviar("SALIR"));
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
