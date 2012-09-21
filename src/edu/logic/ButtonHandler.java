/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import edu.api.CommandInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author lmparra
 */
public class ButtonHandler implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {    
    CommandInterface commandObject = (CommandInterface) e.getSource();
    commandObject.processEvent();
  }
  
}
