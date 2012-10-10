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
public class SignSaveButton extends JButton implements CommandInterface{

    SignSaveButton(String text) {
        super(text);
    }

    @Override
    public void processEvent() {
    }
    
}
