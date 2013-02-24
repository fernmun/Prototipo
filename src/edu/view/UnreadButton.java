package edu.view;

import edu.api.gui.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author david
 */
public class UnreadButton extends JButton implements CommandInterface{

    UnreadButton(String text) {
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
