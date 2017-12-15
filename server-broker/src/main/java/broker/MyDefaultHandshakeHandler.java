package broker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Darren on 2017-12-15
 **/

public class MyDefaultHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        return super.determineUser(request, wsHandler, attributes);
        final HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        System.err.println(request.getHeaders().keySet().size() + "+++");
        HttpHeaders headers = request.getHeaders();

        if (0 != headers.keySet().size()) {
            for (String key : headers.keySet()) {
                System.err.println(key + "  : " + headers.get(key));
            }

        }
        return new Principal() {
            @Override
            public String getName() {
                return "abc";
            }
        };
    }

}
