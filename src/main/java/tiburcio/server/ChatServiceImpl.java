package tiburcio.server;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import tiburcio.client.ChatService;
import tiburcio.server.chan.ChanReader;

import com.google.common.base.Optional;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
public class ChatServiceImpl extends RemoteServiceServlet implements
    ChatService {
  private static final long serialVersionUID = 88310918170927625L;

  private static final Logger log = Logger.getLogger(
      ChatServiceImpl.class.getSimpleName());
      
  private final ChanReader reader;

  @Inject
  ChatServiceImpl(ChanReader reader) {
    this.reader = reader;
  }

  @Override
  public Optional<String> chat(String chat) {
    log.info("Got message: " + chat);
    return Optional.of(chat);
  }
}
