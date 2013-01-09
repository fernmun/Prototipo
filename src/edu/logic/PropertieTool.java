/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

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
 * The <code>PropertieTool</code> Class extends <code>Properties</code> to 
 * make easier instance way.
 */
public class PropertieTool extends Properties{
    
    private InputStream inputStream;
    
    public PropertieTool(String fileName) throws IOException {
        try {
            inputStream = new FileInputStream(fileName);
            this.load(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertieTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
