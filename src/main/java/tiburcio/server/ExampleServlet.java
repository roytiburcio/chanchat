package tiburcio.server;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class ExampleServlet extends HttpServlet {
  private static final long serialVersionUID = 3812418980001267168L;

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().write("Hello World");
    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
