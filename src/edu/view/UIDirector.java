package edu.view;

import edu.api.gui.UIBuilder;

/**
 *
 * @author lmparra
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