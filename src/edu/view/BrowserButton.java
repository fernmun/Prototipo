package edu.view;

import edu.api.gui.CommandInterface;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class BrowserButton extends JButton implements CommandInterface{

    private JTextField txtBrowser;
    private int iSelectionMode = JFileChooser.FILES_AND_DIRECTORIES;
    
    public BrowserButton(String text, JTextField field, int selectionMode) {
        super(text);
        txtBrowser = field;
        iSelectionMode = selectionMode;
    }
    
    public BrowserButton(String text, JTextField field) {
        super(text);
        txtBrowser = field;
    }


    /**
     *
     */
    @Override
    public void processEvent() {
        JFileChooser jfcSelectorArchivo = new JFileChooser();
        jfcSelectorArchivo.setFileSelectionMode(iSelectionMode);
        int iSeleccion = jfcSelectorArchivo.showOpenDialog(null);
        if(iSeleccion == JFileChooser.APPROVE_OPTION){
            File archivo = jfcSelectorArchivo.getSelectedFile();
            txtBrowser.setText(archivo.getAbsolutePath());
        }
    }
    
}
