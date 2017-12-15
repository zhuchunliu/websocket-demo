package client;

import javax.websocket.*;
import java.net.URI;

/**
 *
 * 数据格式参考STOMP协议格式
 *
 * Created by Darren on 2017-12-13
 **/
public class BorkerClient {

    static final char lf = 10; // 这个是换行
    static final char nl = 0; // 这个是消息结尾的标记，一定要


    public static void sendMsg() throws Exception{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8081/client";
        Session session = container.connectToServer(BorkerClientHandler.class, new URI(uri));

        StringBuilder sb = new StringBuilder();
        sb.append("SEND").append(lf); // 请求的命令策略
        sb.append("destination:/app/hello").append(lf); // 请求的资源
        sb.append("content-length:14").append(lf).append(lf); // 消息体的长度
        sb.append("{\"name\":\"123\"}").append(nl); // 消息体

        session.getBasicRemote().sendText(sb.toString()); // 发送消息

        Thread.sleep(50000); // 等待一小会
        session.close(); // 关闭连接
    }



    public static void subscribe() throws Exception{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8081/client";
        Session session = container.connectToServer(BorkerClientHandler.class, new URI(uri));

        StringBuilder sb = new StringBuilder();
        sb.append("SUBSCRIBE").append(lf); // 请求的命令策略
        sb.append("destination:/app/init").append(lf); // 请求的资源
//        sb.append("content-length:14").append(lf).append(lf); // 消息体的长度
        sb.append(nl);
//        sb.append("{\"name\":\"123\"}").append(nl); // 消息体

        session.getBasicRemote().sendText(sb.toString()); // 发送消息

        Thread.sleep(50000); // 等待一小会
        session.close(); // 关闭连接
    }

    public static void main(String[] args) throws Exception {
//        BorkerClient.subscribe();
        BorkerClient.sendMsg();
    }

}
