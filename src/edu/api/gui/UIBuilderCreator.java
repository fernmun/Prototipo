/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api.gui;

/**
 *
 * @author lmparra
 */
public interface UIBuilderCreator {
  
    /**
     *
     * @param object
     * @return
     */
    public UIBuilder getBuilder(String object);
  
}
