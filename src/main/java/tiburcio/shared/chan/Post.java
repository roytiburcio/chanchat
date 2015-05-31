package tiburcio.shared.chan;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

public class Post {
  int no;
  String com;
  public int getNo() {
    return no;
  }
  
  @Nullable
  public String getCom() {
    return com;
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(Post.class)
        .add("no", no)
        .add("com", com)
        .toString();
  }
}