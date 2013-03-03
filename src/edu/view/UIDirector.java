package edu.view;

import edu.api.gui.UIBuilder;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class UIDirector {
  private UIBuilder builder;

    /**
     *
     * @param bldr
     */
    public UIDirector(UIBuilder bldr) {
    builder = bldr;
  }
    /**
     *
     */
    public void build() {
    builder.addUIControls();
    builder.initialize();
  }

}