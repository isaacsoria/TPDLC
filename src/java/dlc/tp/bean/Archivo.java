/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.bean;

import java.util.ArrayList;

/**
 *
 * @author Isaac
 */
public class Archivo {

    ArrayList<Palabra> palabras;
    String nombre;

    public Archivo(String nombre) {
        this.nombre = nombre;
        this.palabras = new ArrayList<>();

    }

    public ArrayList<Palabra> getPalabras() {
        return palabras;
    }

    public void setPalabras(ArrayList<Palabra> palabras) {
        this.palabras = palabras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //hola

}
