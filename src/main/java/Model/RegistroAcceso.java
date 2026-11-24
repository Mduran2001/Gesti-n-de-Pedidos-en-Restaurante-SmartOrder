/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
/**
 *
 * @author casti
 */
public class RegistroAcceso {

    private int idAcceso;
    private int idUsuario;
    private LocalDateTime fechaHora;

    public RegistroAcceso() {
    }

    public RegistroAcceso(int idAcceso, int idUsuario, LocalDateTime fechaHora) {
        this.idAcceso = idAcceso;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
    }

    public int getIdAcceso() { return idAcceso; }
    public void setIdAcceso(int idAcceso) { this.idAcceso = idAcceso; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
