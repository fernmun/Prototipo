package edu.logic.gui;

import edu.api.gui.CommandInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * <code>ButtonHandler</code> Class  
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class ButtonHandler implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {    
    CommandInterface commandObject = (CommandInterface) e.getSource();
    commandObject.processEvent();
  }
  
}
