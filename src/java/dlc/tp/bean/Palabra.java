/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.bean;

import java.util.Objects;

/**
 *
 * @author kike
 */
public class Palabra {

    private String palabra;
    private int frecuencia;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void up1Cantidad() {
        this.frecuencia++;
    }

    public Palabra(String palabra, int cantidad) {
        this.palabra = palabra;
        this.frecuencia = cantidad;
    }

    @Override
    public int hashCode() {
        return palabra.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Palabra) {
            Palabra p = (Palabra) obj;
            return this.palabra.equals(p.palabra);
        } else {
            return false;
        }
    }

}
