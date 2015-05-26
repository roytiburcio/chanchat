package tiburcio.shared.chan;
import java.util.Arrays;

import com.google.common.base.MoreObjects;

public class Thread {
  Post[] posts;
  public Post[] getPosts() {
    return posts;
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(Thread.class)
      .add("posts", Arrays.toString(posts))
      .toString();
  }
}