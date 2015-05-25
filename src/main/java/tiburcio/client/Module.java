package tiburcio.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Module implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HomePage homePage = new HomePage();
    RootPanel.get().add(homePage);
  }
}
