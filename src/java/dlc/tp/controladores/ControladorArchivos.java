/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp.controladores;
import dlc.tp.db.AccesoDatos;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

/**
 *
 * @author Isaac
 */
public class ControladorArchivos extends SwingWorker<String, Void> {

    @Override
    protected String doInBackground() throws Exception {

        File file[] = null;
        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return (f.isFile() && f.getName().endsWith(".txt")) || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Archivos de Texto";
            }
        });
        int returnVal = jf.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jf.getSelectedFiles();
        }
        for (int i = 0; i < file.length; i++) {
            File next = file[i];
            AccesoDatos.insertarPalabras(Manejador.procesarArchivo(next), next.getName());

        }

        return null;
    }
}
