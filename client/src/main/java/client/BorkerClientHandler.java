package client;

import javax.websocket.*;

/**
 * Created by Darren on 2017-12-13
 **/
@ClientEndpoint
public class BorkerClientHandler {
    @OnOpen
    public void  onOpen(Session session){
        System.err.println("open 打开连接");
    }

    @OnError
    public void onError(Throwable throwable){
        System.err.println("err 连接异常");
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(){
        System.err.println("close 关闭连接");
    }

    @OnMessage
    public void onMessage(String message){
        System.err.println(message);
    }
}
