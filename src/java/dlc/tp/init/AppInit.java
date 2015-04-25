/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.init;

import dlc.tp.db.Conexion;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.jboss.logging.Logger;

/**
 *
 * @author Rodri
 */
public class AppInit implements ServletContextListener {

    private ServletContext sc = null;
    private static final Logger log = Logger.getLogger(AppInit.class);

    @Override
    public void contextInitialized(ServletContextEvent ctx) {
        this.sc = ctx.getServletContext();
        try {
            //cargar
            Conexion.insertar("INSERT INTO ARCHIVO (nombre) VALUES ('prueba2.txt');");
            ResultSet prueba = Conexion.consultar("SELECT * FROM ARCHIVO");
            while(prueba.next()){
                log.info("Elemento - Id: "+prueba.getString("idArchivo")+" , Val: "+prueba.getString("nombre"));
            }
        } catch (Exception e) {
            log.error("oops", e);
        }
        log.info("webapp started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent ctx) {
        try {
            // shutdown code
        } catch (Exception e) {
            log.error("oops", e);
        }
        this.sc = null;
        log.info("webapp stopped");
    }
}
