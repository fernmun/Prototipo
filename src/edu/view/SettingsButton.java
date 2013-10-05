package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.gui.Mediator;
import javax.swing.JButton;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SettingsButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    public SettingsButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }


    /**
     *
     */
    @Override
    public void processEvent() {
        mediator.saveSettings();
    }
    
}
