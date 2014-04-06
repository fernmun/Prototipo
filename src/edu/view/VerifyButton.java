/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.gui.Mediator;
import javax.swing.JButton;

/**
 *
 * @author DavidCamilo
 */
public class VerifyButton extends JButton implements CommandInterface{

    private Mediator mediator;
    
    public VerifyButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }
    
    @Override
    public void processEvent() {
        mediator.verifyDocument();
    }
    
}
