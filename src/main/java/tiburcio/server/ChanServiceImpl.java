package tiburcio.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jibble.jmegahal.JMegaHal;
import org.jsoup.Jsoup;

import tiburcio.client.ChanService;
import tiburcio.server.chan.ChanReader;
import tiburcio.shared.chan.Board;
import tiburcio.shared.chan.Post;
import tiburcio.shared.chan.Thread;

import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
public class ChanServiceImpl extends RemoteServiceServlet implements
    ChanService {

  private static final long serialVersionUID = 3494059890980613673L;
  private static final Logger log = Logger.getLogger(ChanServiceImpl.class.getSimpleName());
  private final ChanReader reader;
  private final JMegaHal hal;

  @Inject
  private ChanServiceImpl(ChanReader reader, JMegaHal hal) {
    this.reader = reader;
    this.hal = hal;
  }

  private List<String> getComments() throws IOException {
    Board board = reader.read("b", 1);
    List<String> comments = Lists.newArrayList();
    for (Thread thread : board.getThreads()) {
      for (Post post : thread.getPosts()) {
        String comment = post.getCom();
        if (comment != null) {
          comments.add(removeXRefs(Jsoup.parse(comment).text()));
        }
      }
    }
    return comments;
  }
  
  private static String removeXRefs(String str) {
    return str.replaceAll(">>\\d*\\s", "");
  }

  @Override
  public boolean learn()  {
    List<String> comments;
    try {
      comments = getComments();
    } catch (IOException e) {
      log.info("Could not reach 4chan.");
      e.printStackTrace();
      return false;
    }
    int count = 0;
    for (String comment : comments) {
      hal.add(comment);
      count++;
    }
    log.info(String.format("Loaded %d comments.", count));
    return true;
  }
}
