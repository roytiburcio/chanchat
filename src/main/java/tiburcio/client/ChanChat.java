package tiburcio.client;

import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
  
  private ChatServiceAsync chatService = GWT.create(ChatService.class);
  private ChanServiceAsync chanService = GWT.create(ChanService.class);
  
  public ChanChat() {
    initWidget(binder.createAndBindUi(this));
    chanService.learn(new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Learning Failed", caught);
      }

      @Override
      public void onSuccess(Boolean result) {
        GWT.log("Success in learning.");
      }
    });
  }
  
  @UiHandler("sendButton")
  void onSendButtonClick(ClickEvent c) {
    chat();
  }

  private void chat() {
    chatService.chat(inputBox.getText(), new AsyncCallback<Optional<String>>() {
      @Override
      public void onSuccess(Optional<String> result) {
        chatBox.setText(chatBox.getText() + "\n" + inputBox.getText() + "\n--" + result.get());
        inputBox.setText("");
      }
      
      @Override
      public void onFailure(Throwable caught) {
        GWT.log("Could not chat", caught);
      }
    });
  }
  
  @UiHandler("inputBox")
  void onEnterPress(KeyDownEvent e) {
    if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
      chat();
    }
  }
}