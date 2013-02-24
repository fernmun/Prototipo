/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.tools;

import edu.view.SignSendButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lmparra
 * 
 * The <code>PropertiesTool</code> Class extends <code>Properties</code> to 
 * make easier instance way.
 */
public class PropertiesTool extends Properties{
    
    private InputStream inputStream;
    
    public PropertiesTool(String fileName) throws IOException {
        try {
            inputStream = new FileInputStream(fileName);
            this.load(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
