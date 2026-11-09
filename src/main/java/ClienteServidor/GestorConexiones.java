/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClienteServidor;

import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mario
 */
public class GestorConexiones {

    private final Set<Socket> clientes = ConcurrentHashMap.newKeySet();

    public boolean registrar(Socket cliente) {
        return clientes.add(cliente);
    }

    public void retirar(Socket cliente) {
        clientes.remove(cliente);
    }

    public int cantidadConectados() {
        return clientes.size();
    }
}
