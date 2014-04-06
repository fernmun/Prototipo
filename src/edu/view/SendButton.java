package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.WebServicesProvider;
import edu.logic.gui.Mediator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SendButton extends JButton implements CommandInterface{

    Mediator mediator;
    
    SendButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        try {
            mediator.sendDocument();
        } catch (Exception ex) {
            Logger.getLogger(SendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
