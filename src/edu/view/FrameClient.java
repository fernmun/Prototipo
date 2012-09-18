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
  private BuilderFactory factory;
  private UIDirector director;
          
  public FrameClient(int width, int height, String title, int posX, int posY, String type) {
    // Set all needed properties
    this.setBounds(posX, posY, width, height);
    this.setTitle(title);
    this.setVisible(true);
    
    factory = new BuilderFactory();
    director = new UIDirector(factory.getBuilder(type));
    
  }

  FrameClient(int i, int i0, String ventana_de_prueba, int i1, int i2) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
}
