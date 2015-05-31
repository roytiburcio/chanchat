package tiburcio.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import tiburcio.client.ChanService;
import tiburcio.client.ChatService;
import tiburcio.server.parser.ChanParser;

import com.google.common.base.Optional;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
public class ChatServiceImpl extends RemoteServiceServlet implements
    ChatService {
  private static final long serialVersionUID = 88310918170927625L;

  private static final Logger log = Logger.getLogger(
      ChatServiceImpl.class.getSimpleName());
      
  private final ChanParser parser;
  private final ChanService chanService;

  @Inject
  ChatServiceImpl(ChanService chanService, ChanParser parser) {
    this.chanService = chanService;
    this.parser = parser;
  }

  @Override
  public Optional<String> chat(String chat) {
    log.info("Got message: " + chat);
    try {
      log.info("Comments:\n" + chanService.getComments());
    } catch (IOException e) {
      log.info("Could not reach 4chan.");
      e.printStackTrace();
    }
    parser.extractNoun(chat);
    return Optional.of(chat);
  }
}
