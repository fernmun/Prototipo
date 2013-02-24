package edu.view;

import edu.api.gui.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class SendButton extends JButton implements CommandInterface{

    SendButton(String text) {
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
