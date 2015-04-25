/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.controladores;

import dlc.tp.bean.Palabra;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author kike
 */
public class Manejador {

    public static HashMap<Integer, Palabra> procesarArchivo(File archivo) {
        HashMap<Integer, Palabra> mapa = new HashMap();
        try (Scanner sc = new Scanner(archivo, "UTF-8")) {
            Pattern pattern = Pattern.compile("[^a-zA-Zá-ú]");
//       sc.useDelimiter("[﻿@\"\t\n.:,-;¿?!¡<>«»$%()_  *]");
            sc.useDelimiter(pattern);
            String a = "";
            while (sc.hasNext()) {
                a = remove1(sc.next()).toLowerCase();

                if (!a.isEmpty() && a.length() > 1) {
                    Palabra pal = new Palabra(a, 1);

                    if (mapa.containsKey(pal.hashCode())) {
                        mapa.get(a.hashCode()).up1Cantidad();
                    } else {
                        mapa.put(pal.hashCode(), pal);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de entrada...");
        }
        return mapa;
    }

    private static String remove1(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
    
    

}
