package tiburcio.client;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chan")
public interface ChanService extends RemoteService {
  List<String> getComments() throws IOException;
}
