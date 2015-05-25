package tiburcio.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ChanChat extends Composite {
  interface Binder extends UiBinder<HTMLPanel, ChanChat> {}
  final Binder binder = GWT.create(Binder.class);
  @UiField Button sendButton;
  @UiField TextArea chatBox;
  @UiField TextBox inputBox;
  
  public ChanChat() {
    initWidget(binder.createAndBindUi(this));
  }
}