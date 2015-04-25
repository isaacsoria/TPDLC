package dlc.tp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.jboss.logging.Logger;

public class Conexion {

    public static final Logger log = Logger.getLogger(Conexion.class);
    
    public static Connection con;
    public static Statement stat;
    public static String rutaDb = "jdbc:sqlite:C:\\Users\\Rodri\\Documents\\UTN\\DLC\\TpDLC\\Db\\TpDlcDb.s3db";
    
    public static void conectar(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(rutaDb);
            stat = con.createStatement();
            Conexion.log.info("Conexion realizada");
        } catch (SQLException ex) {
            Conexion.log.error("SQL EXCEPTION: "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Conexion.log.error("ClassNotFound EXCEPTION: "+ex.getMessage());
        }
    }
    
    public static void cerrar(){
        try{
            stat.close();
            con.close();
        } catch(SQLException ex){
            Conexion.log.error("SQL EXCEPTION: "+ex.getMessage());
        }  
    }
    
    public static boolean insertar(String sql){
        boolean val = true;
        Conexion.conectar();
        try{
            Conexion.stat.executeUpdate(sql);
        } catch(SQLException ex){
            val = false;
            Conexion.log.error("SQL EXCEPTION: "+ex.getMessage());
        }
        Conexion.cerrar();
        return val;
    }
    
    public static ResultSet consultar(String sql){
        Conexion.conectar();
        ResultSet consulta = null;
        try{
            consulta = Conexion.stat.executeQuery(sql);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return consulta;
    }
    
    
}
