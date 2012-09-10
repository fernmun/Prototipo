/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import  javax.swing.JFrame;

/**
 *
 * @author lmparra
 */
public class FrameClient extends JFrame{
  
  private int width, height, posX, posY;
  private String title;
  
  public FrameClient(int width, int height, String title, int posX, int posY) {
    // Set all needed properties
    this.setBounds(posX, posY, width, height);
    this.setTitle(title);
    this.setVisible(true);
    
  }
  
}
