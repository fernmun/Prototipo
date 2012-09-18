/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author lmparra
 */

public abstract class UIBuilder {
  protected JPanel panelUI;

  public abstract void addUIControls();
  public abstract void initialize();

  public JPanel getPanel() {
    return panelUI;
  }
}
