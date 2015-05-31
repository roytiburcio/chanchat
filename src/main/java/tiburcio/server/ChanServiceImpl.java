package tiburcio.server;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
  private final ChanReader reader;

  @Inject
  private ChanServiceImpl(ChanReader reader) {
    this.reader = reader;
  }

  @Override
  public List<String> getComments() throws IOException {
    Board board = reader.read("b", 1);
    List<String> comments = Lists.newArrayList();
    for (Thread thread : board.getThreads()) {
      for (Post post : thread.getPosts()) {
        String comment = post.getCom();
        if (comment != null) {
          comments.add(comment);
        }
      }
    }
    return comments;
  }
}
