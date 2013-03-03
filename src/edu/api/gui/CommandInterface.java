package edu.api.gui;

/**
 *
 * <code>CommandInterface</code> interface is an abstraction to implement Command
 * pattern to handle events of all UI components.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public interface CommandInterface {
  
    /**
     *
     * Running the code identified by the command object's <i>execute</i> method
     * 
     */
    public void processEvent();
  
}
