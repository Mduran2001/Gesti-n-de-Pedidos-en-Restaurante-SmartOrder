/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClienteServidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 *
 * @author mario
 */
public class ServidorSocket {

    public static final int PUERTO = 5000;
    private final GestorConexiones gestor = new GestorConexiones();

    public void iniciar() throws IOException {
        ExecutorService trabajadores = Executors.newFixedThreadPool(5);
        try (ServerSocket servidor = new ServerSocket(
                PUERTO, 5, InetAddress.getLoopbackAddress())) {
            System.out.println("Servidor SmartOrder iniciado en localhost:" + PUERTO);
            while (!Thread.currentThread().isInterrupted()) {
                Socket cliente = servidor.accept();
                if (gestor.cantidadConectados() >= 5) {
                    cliente.close();
                    continue;
                }
                gestor.registrar(cliente);
                try {
                    trabajadores.execute(new ClienteHandler(cliente, gestor));
                } catch (RejectedExecutionException e) {
                    gestor.retirar(cliente);
                    cliente.close();
                }
            }
        } finally {
            trabajadores.shutdownNow();
        }
    }

    public static void main(String[] args) {
        try {
            new ServidorSocket().iniciar();
        } catch (IOException e) {
            System.err.println("No se pudo iniciar el servidor: " + e.getMessage());
        }
    }
}
