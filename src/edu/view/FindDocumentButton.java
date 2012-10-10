/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.CommandInterface;
import javax.swing.JButton;

/**
 *
 * @author lmparra
 */
public class FindDocumentButton extends JButton implements CommandInterface{

    FindDocumentButton(String text) {
        super(text);
    }

  @Override
  public void processEvent() {
    
    
    
  }
  
}
