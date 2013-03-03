package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.gui.Mediator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SignSendButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    SignSendButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        try {
            mediator.signSendDocument();
        } catch (Exception ex) {
            Logger.getLogger(SignSendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
