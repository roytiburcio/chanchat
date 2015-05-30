package tiburcio.server;

import javax.servlet.annotation.WebListener;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

@WebListener
public class GuiceConfig extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(
        new ChanServletModule(),
        new AbstractModule() {
          @Override
          protected void configure() {
            bind(Gson.class).toInstance(new Gson());
          }
        });
  }
}
