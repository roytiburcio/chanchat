package tiburcio.server;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

@Singleton
public class ChanServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    serve("*.asdf").with(ExampleServlet.class);
    serve("/Module/chat").with(ChatServiceImpl.class);
  }
}
