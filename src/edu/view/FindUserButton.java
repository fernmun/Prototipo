package edu.view;

import edu.api.gui.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class FindUserButton extends JButton implements CommandInterface{

    FindUserButton(String text) {
        super (text);
    }

    @Override
    public void processEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
