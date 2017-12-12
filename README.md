## 1、client端

java端模拟client请求，注意jar包：
```
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-websocket</artifactId>
    <version>RELEASE</version>
</dependency>
```

## 2、基于@ServerEndpoint标签的server 端

Bean配置：

```
@Bean
public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
}
```

3.2、处理器实现
```
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
```