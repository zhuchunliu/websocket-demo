package server;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Darren on 2017-12-11
 **/
public class SocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.err.println("建立连接"+session.getRemoteAddress().getHostName());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.err.println("接受内容: "+message.getPayload());
        session.sendMessage(new TextMessage((message.getPayload()+"收到了").getBytes()));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//        super.handleBinaryMessage(session, message);
        ByteBuffer buffer = message.getPayload();
        buffer.flip();
        while(buffer.hasRemaining()){
            byte[] bytes =new byte[buffer.limit()];
            buffer.get(bytes);
            System.err.println("receive mesg : "+new String(bytes));
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.err.println("关闭连接");
    }
}
