package edu.view;

import edu.api.gui.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class ReadButton extends JButton implements CommandInterface{

    ReadButton(String text) {
        super(text);
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
