package broker;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

/**
 * Created by Darren on 2017-12-13
 **/
@Configuration
@EnableWebSocketMessageBroker
public class BrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * registry.enableSimpleBroker是使用内存中介，不依赖第三方组件，
     * registry.enableStompBrokerRelay是使用第三方中间件，需要事先下载安装中间件，如activeMQ/RabbitMQ
     * registry.applicationDestinationPrefixes 应用前缀，所有请求的消息将会路由到@MessageMapping的controller上
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 应用程序以 /app 为前缀，而 代理目的地以 /topic 为前缀.
        registry.enableSimpleBroker("/topic","/topicAbc","/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");

        // 下面这配置为默认配置，如有变动修改配置启用就可以了
//        registry.enableStompBrokerRelay("/topic", "/queue")
//      .setRelayHost("127.0.0.1") //activeMq服务器地址
//      .setRelayPort(61613)//activemq 服务器服务端口
//      .setClientLogin("guest")    //登陆账户
//      .setClientPasscode("guest");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //为java stomp client提供链接
        registry.addEndpoint("/client")
                .setHandshakeHandler(new MyHandshakeHandler())
                .addInterceptors(new MyHandshakeInterceptor());

        //为js客户端提供链接
        //必须加上setAllowedOrigins("*"),否则报错： DefaultSockJsService Origin header value not allowed
//        registry.addEndpoint("/client").setHandshakeHandler(new MyDefaultHandshakeHandler()).setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/client").setAllowedOrigins("*").withSockJS();

    }

    /**
     * 消息传输参数配置
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        super.configureWebSocketTransport(registration);
        registration.setMessageSizeLimit(8192).setSendBufferSizeLimit(8192).setSendTimeLimit(10000);
    }

    /**
     * 输入通道参数设置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
//        super.configureClientInboundChannel(registration);
        registration.taskExecutor().corePoolSize(4).maxPoolSize(100).keepAliveSeconds(60);
    }

    /**
     * 输出通道参数设置
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        super.configureClientOutboundChannel(registration);
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);

    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }
}
