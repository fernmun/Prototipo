/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.CommandInterface;
import edu.logic.Mediator;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class SignSaveButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    SignSaveButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    @Override
    public void processEvent() {
        mediator.signDocument();
    }
    
}
