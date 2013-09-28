package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.gui.Mediator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SignSaveButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    SignSaveButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        try {
            mediator.signDocument();
        } catch (IOException ex) {
            Logger.getLogger(SignSaveButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
