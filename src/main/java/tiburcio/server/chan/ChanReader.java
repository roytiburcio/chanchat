package tiburcio.server.chan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import tiburcio.shared.chan.Board;
import tiburcio.shared.chan.Thread;

import com.google.gson.Gson;

public class ChanReader {
  private static final Logger log = Logger.getLogger(ChanReader.class.getSimpleName());
  private final Gson gson;

  public ChanReader(Gson gson) {
    this.gson = gson;
  }
  
  public String read(String board, int pageNumber) throws IOException {
    String pageUrl = String.format("http://a.4cdn.org/%s/%d.json", board, pageNumber);
    log.info("Reading from URL: " + pageUrl);

    URL url = new URL(pageUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    Board boardContents = gson.fromJson(reader, Board.class);
    reader.close();
    return boardContents.toString();
  }
  
  public static void main(String arg[]) throws IOException {
    ChanReader reader = new ChanReader(new Gson());
    log.info(reader.read("b", 1));
  }
}