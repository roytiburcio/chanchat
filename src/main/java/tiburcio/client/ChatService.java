package tiburcio.client;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService {
  Optional<String> chat(String chat);
}
