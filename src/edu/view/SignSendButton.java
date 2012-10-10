/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class SignSendButton extends JButton implements CommandInterface{

    SignSendButton(String text) {
        super(text);
    }

    @Override
    public void processEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
