package edu.view;

import edu.api.gui.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FindUserButton extends JButton implements CommandInterface{

    FindUserButton(String text) {
        super (text);
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
