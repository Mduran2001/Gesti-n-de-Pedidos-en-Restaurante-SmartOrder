/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author casti
 */
public class Cocina {

    private int idCocina;
    private String nombre;
    private String area;
    private String estado;

    public Cocina() {
    }

    public Cocina(int idCocina, String nombre, String area, String estado) {
        this.idCocina = idCocina;
        this.nombre = nombre;
        this.area = area;
        this.estado = estado;
    }

    public int getIdCocina() {
        return idCocina;
    }

    public void setIdCocina(int idCocina) {
        this.idCocina = idCocina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}