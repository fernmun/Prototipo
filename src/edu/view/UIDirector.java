package edu.view;

import edu.api.gui.UIBuilder;

/**
 *
 * @author lmparra
 */
public class UIDirector {
  private UIBuilder builder;

  public UIDirector(UIBuilder bldr) {
    builder = bldr;
  }
  public void build() {
    builder.addUIControls();
    builder.initialize();
  }

}