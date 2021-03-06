package tiburcio.server;

import java.io.File;
import java.net.URISyntaxException;

import javax.servlet.annotation.WebListener;

import org.jibble.jmegahal.JMegaHal;

import tiburcio.client.ChanService;
import tiburcio.server.parser.ChanParser;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

@WebListener
public class GuiceConfig extends GuiceServletContextListener {
  private static final String CHUNKING_FILE = "tiburcio/server/en-parser-chunking.bin";
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(
        new ChanServletModule(),
        new AbstractModule() {
          @Override
          protected void configure() {
            bind(Gson.class).toInstance(new Gson());
            bind(ChanService.class).to(ChanServiceImpl.class);
            bind(JMegaHal.class).toInstance(new JMegaHal());
            try {
              bind(File.class).annotatedWith(ChanParser.ParseModelFile.class) 
                  .toInstance(new File(Resources.getResource(CHUNKING_FILE).toURI()));
            } catch (URISyntaxException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }
}
