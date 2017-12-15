package client;

import javax.websocket.*;
import java.net.URI;

/**
 * Created by Darren on 2017-12-11
 **/
public class Client {
    public static void main(String[] args) throws Exception{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();//
        String uri = "ws://127.0.0.1:8080/log";
        Session session = container.connectToServer(ClinetHandler.class,new URI(uri));
        session.getBasicRemote().sendText("abc123");

        Thread.sleep(100000);
        session.close();

    }

}
