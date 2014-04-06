package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.gui.Mediator;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FindUserButton extends JButton implements CommandInterface{

    Mediator mediator;
    
    FindUserButton(String text, Mediator m) {
        super (text);
        mediator = m;
    }

    /**
     *
     */
    @Override
    public void processEvent() {
        new UserChooseFrame(400, 150, "Elegir Usuario", 250, 50, mediator);
    }
    
}
