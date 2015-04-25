/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.db;

import dlc.tp.bean.Palabra;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JProgressBar;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author kike
 */
public class AccesoDatos {

    private static int contador = 0;

    public static void setContador() {
        contador = 0;
    }

    public static void insertarPalabras(HashMap mapa, String archivo) {
        int idPalabra = 0;
        int idArchivo = 0;
        ResultSet resultSet;
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:derby:Diccionario2");
            conexion.setAutoCommit(false);
            idArchivo = insertarArchivo(conexion, archivo);
            String generatedColumns[] = {"IDPALABRA"};
            PreparedStatement insertPalabra = conexion.prepareStatement("Insert into PALABRA ( PALABRA ) VALUES (?) ", generatedColumns);
            PreparedStatement selectIdPalabra = conexion.prepareStatement("select idpalabra from  palabra  where PALABRA=?");
            PreparedStatement insertArchivoXPalabra = conexion.prepareStatement("insert into archivoxpalabra  (archivo, palabra) values (?, ?)");
            Iterator i = mapa.values().iterator();
            while (i.hasNext()) {
                Palabra pal = (Palabra) i.next();
                insertPalabra.setString(1, pal.getPalabra());
                insertPalabra.setInt(2, pal.getFrecuencia());
                try {
                    // Inserto la palabra y recupero el id autogenerado para luego insertar en archivoXpalabra
                    //Si el insert falla xq la palabra ya esta cargada entra al catch y actualiza la frecuencia
                    insertPalabra.executeUpdate();
                    resultSet = insertPalabra.getGeneratedKeys();
                    if (resultSet.next()) {
                        idPalabra = resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    // actualizo la frecuencia y recupero el id
//                    updatePalabra.setInt(1, pal.getFrecuencia());
//                    updatePalabra.setString(2, pal.getPalabra());
//                    updatePalabra.executeUpdate();
                    selectIdPalabra.setString(1, pal.getPalabra());
                    resultSet = selectIdPalabra.executeQuery();
                    if (resultSet.next()) {
                        idPalabra = resultSet.getInt(1);
                    }
                }
                // inserto en archivoXpalabra
                insertArchivoXPalabra.setInt(1, idArchivo);
                insertArchivoXPalabra.setInt(2, idPalabra);
                insertArchivoXPalabra.executeUpdate();
            }
            //Una vez procesadas todas las palabras, hago commit
            conexion.commit();
            insertArchivoXPalabra.close();
            insertPalabra.close();
//            updatePalabra.close();
            insertPalabra.close();
            conexion.close();
        } catch (SQLException ex) {
            // si sucede algun error , hago rollback
            try {
                conexion.rollback();
            } catch (SQLException ex2) {
                System.out.println("Error " + ex2.getMessage());
            }
            System.out.println("Error " + ex.getMessage());
        }
        contador += 100;

    }

    public static int insertarArchivo(Connection conexion, String archivo) {
        int idArchivo = 0;
        try {
            String generatedColumns[] = {"IDARCHIVO"};

            ResultSet resultSet;

            PreparedStatement insertArchivo = conexion.prepareStatement("Insert into ARCHIVO ( ARCHIVO) VALUES (?) ", generatedColumns);
            Statement statement = conexion.createStatement();
            insertArchivo.setString(1, archivo);

            insertArchivo.executeUpdate();
            resultSet = insertArchivo.getGeneratedKeys();
            if (resultSet.next()) {
                idArchivo = resultSet.getInt(1);
            }

            insertArchivo.close();
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }

        return idArchivo;
    }

    public static boolean existeArchivo(String archivo) {
        boolean retorno = false;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:derby:Diccionario2");
            PreparedStatement existeArchivo = conexion.prepareStatement("select idarchivo from archivo where archivo=?");
            existeArchivo.setString(1, archivo);
            ResultSet resultSet = existeArchivo.executeQuery();
            if (resultSet.next()) {
                retorno = true;
            }
            existeArchivo.close();
            resultSet.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
            return retorno;
        }
        return retorno;
    }

    public static ResultSet obtenerPalabras() {
        ResultSet rs = null;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:derby:Diccionario2");
            PreparedStatement obtenerPalabras = conexion.prepareStatement("select palabra.idpalabra,palabra.palabra, palabra.frecuencia , count(*) from palabra join archivoxpalabra on palabra.idpalabra=archivoxpalabra.palabra GROUP BY  palabra.idpalabra,palabra.palabra, palabra.frecuencia");
            rs = obtenerPalabras.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return rs;

    }

    public static String obtenerArchivoXPalabra(int id) {
        Connection conexion = null;
        PreparedStatement a = null;
        ResultSet rs = null;
        try {
            conexion = DriverManager.getConnection("jdbc:derby:Diccionario2");
            a = conexion.prepareStatement("SELECT a.archivo FROM archivo a, archivoxpalabra axp WHERE a.idarchivo = axp.archivo AND axp.palabra =?");
            a.setInt(1, id);
            rs = a.executeQuery();
            StringBuilder r = new StringBuilder("Archivos: ");
            while (rs.next()) {
                r.append("\n");
                r.append(rs.getString(1));
            }
            conexion.close();
            a.close();
            rs.close();
            return r.toString();

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
            return "";

        }

    }

    public static void borrarTodo() {

        try {
            Connection conexion = DriverManager.getConnection("jdbc:derby:Diccionario2");
            conexion.setAutoCommit(false);
            PreparedStatement borrarArchivoXPalabra = conexion.prepareStatement("delete from archivoxpalabra");
            PreparedStatement borrarPalabras = conexion.prepareStatement("delete from palabra");
            PreparedStatement borrarArchivos = conexion.prepareStatement("delete from archivo");
            borrarArchivoXPalabra.executeUpdate();
            borrarPalabras.executeUpdate();
            borrarArchivos.executeUpdate();
            conexion.commit();
            borrarArchivoXPalabra.close();
            borrarArchivos.close();
            borrarPalabras.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }
}
