/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.CommandInterface;
import edu.logic.Mediator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class SignSendButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    SignSendButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    @Override
    public void processEvent() {
        try {
            mediator.signSendDocument();
        } catch (Exception ex) {
            Logger.getLogger(SignSendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
