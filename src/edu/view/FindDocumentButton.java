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
 * @author lmparra
 */
public class FindDocumentButton extends JButton implements CommandInterface{

    Mediator mediator;

    FindDocumentButton(String text, Mediator m) {
        super(text);
        mediator = m;
    }

    @Override
    public void processEvent() {
        mediator.findDocument();
    }
  
}
