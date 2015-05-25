package tiburcio.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ChanChat extends Composite {
  interface Binder extends UiBinder<HTMLPanel, ChanChat> {}
  final Binder binder = GWT.create(Binder.class);
  
  public ChanChat() {
    initWidget(binder.createAndBindUi(this));
  }
}