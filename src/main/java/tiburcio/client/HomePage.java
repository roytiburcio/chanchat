package tiburcio.client;


import tiburcio.shared.FieldVerifier;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class HomePage extends Composite {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  private final Messages messages = GWT.create(Messages.class);
  interface Binder extends UiBinder<HTMLPanel, HomePage> {};
  Binder binder = GWT.create(Binder.class);

  @UiField Button sendButton;
  @UiField TextBox nameField;
  @UiField Label errorLabel;
  @UiField DialogBox dialogBox;
  @UiField HTMLPanel dialogVPanel;
  @UiField Button closeButton;
  @UiField HTML serverResponseLabel;
  @UiField Label textToServerLabel;
  
  public HomePage() {
    initWidget(binder.createAndBindUi(this));

    sendButton.setText(messages.sendButton());
    nameField.setText(messages.nameField());

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    dialogBox.setAnimationEnabled(true);
  }

  @UiHandler("closeButton")
  void handleCloseClick(ClickEvent e) {
    dialogBox.hide();
    sendButton.setEnabled(true);
    sendButton.setFocus(true);
  }
  
  @UiHandler("sendButton")
  void handleClick(ClickEvent e) {
    GWT.log("Clicked");
    sendNameToServer();
  }
  
  @UiHandler("nameField") 
  void handleKey(KeyUpEvent e) {
    if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
      sendNameToServer();
    }
  }

  /**
   * Send the name from the nameField to the server and wait for a response.
   */
  private void sendNameToServer() {
    // First, we validate the input.
    errorLabel.setText("");
    String textToServer = nameField.getText();
    if (!FieldVerifier.isValidName(textToServer)) {
      errorLabel.setText("Please enter at least four characters");
      return;
    }
    // Then, we send the input to the server.
    sendButton.setEnabled(false);
    textToServerLabel.setText(textToServer);
    serverResponseLabel.setText("");
    greetingService.greetServer(textToServer, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        // Show the RPC error message to the user
        dialogBox.setText("Remote Procedure Call - Failure");
        serverResponseLabel.addStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(SERVER_ERROR);
        dialogBox.center();
        closeButton.setFocus(true);
      }

      public void onSuccess(String result) {
        dialogBox.setText("Remote Procedure Call");
        serverResponseLabel.removeStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(result);
        dialogBox.center();
        closeButton.setFocus(true);
      }
    });
  }
}
