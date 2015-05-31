package tiburcio.server;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jibble.jmegahal.JMegaHal;

import tiburcio.client.ChatService;
import tiburcio.server.parser.ChanParser;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
public class ChatServiceImpl extends RemoteServiceServlet implements
    ChatService {
  private static final long serialVersionUID = 88310918170927625L;

  private static final Logger log = Logger.getLogger(
      ChatServiceImpl.class.getSimpleName());
      
  private final ChanParser parser;
  private final JMegaHal hal;

  @Inject
  ChatServiceImpl(ChanParser parser, JMegaHal hal) {
    this.parser = parser;
    this.hal = hal;
  }

  @Override
  public Optional<String> chat(String chat) {
    log.info("Got message: " + chat);
    String noun = parser.extractNoun(chat);

    if (!Strings.isNullOrEmpty(noun)) {
      return Optional.of(hal.getSentence(noun));
    } else {
      return Optional.of(hal.getSentence());
    }
  }
}
