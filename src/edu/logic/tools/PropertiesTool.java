package edu.logic.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * The <code>PropertiesTool</code> Class extends <code>Properties</code> to 
 * make easier instance way.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */ 
public class PropertiesTool extends Properties{
    
    private InputStream inputStream;
    
    /**
     *
     * Read a properties file. 
     * 
     * @param fileName
     *        {@link String} Properties file name.
     * @throws IOException
     */
    public PropertiesTool(String fileName) throws IOException {
        try {
            inputStream = new FileInputStream(fileName);
            this.load(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesTool.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
}
