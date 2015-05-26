package tiburcio.shared.chan;

import java.util.Arrays;

import com.google.common.base.MoreObjects;

public class Board {
  Thread[] threads;

  public Thread[] getThreads() {
    return threads;
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(Board.class)
        .add("threads", Arrays.toString(threads))
        .toString();
  }
}
