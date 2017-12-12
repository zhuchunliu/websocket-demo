package server;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by Darren on 2017-12-12
 **/
@ServerEndpoint(value = "/info/{userCode}")
@Component
public class WebSocketPoint{

    @OnOpen
    public void onOpen(@PathParam("userCode")String code, Session session){
        System.err.println("客户端连接上"+code);
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.err.println("接收消息: "+message);
        session.getBasicRemote().sendText(message+"收到了");
    }
}
