/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api.gui;

import edu.logic.gui.Mediator;
import javax.swing.*;

/**
 *
 * @author lmparra
 */

public abstract class UIBuilder {
    /**
     *
     */
    protected JPanel panelUI;
    /**
     *
     */
    protected Mediator mediatorUI;

    /**
    *
    */
    public abstract void addUIControls();
    /**
    *
    */
    public abstract void initialize();

    /**
    *
    * @return
    */
    public JPanel getPanel() {
        return panelUI;
    }

    /**
    *
    * @param mediator
    */
    public UIBuilder(Mediator mediator){
        mediatorUI = mediator;
    }
}
