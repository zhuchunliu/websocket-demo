package broker;

import broker.entity.Greeting;
import broker.entity.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Darren on 2017-12-13
 **/
@RestController
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 给订阅了/topic/greetings的人群发消息
     */
    @RequestMapping("/send")
    public void send(@RequestParam(value = "name",defaultValue = "james")String name){
        template.convertAndSend("/topicAbc/greetings",new Greeting("群发消息"));
        template.convertAndSendToUser(name,"/queue/nofications",new Greeting("群发消息-"+name));
    }


    @MessageMapping("/index") // 不用@SendTo,则代理返回的订阅地址：/topic/index
    public Greeting indexHandler(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("参数name: "+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/hello")
    @SendTo("/topicAbc/greetings")  // 不设置registry.enableSimpleBroker或者 代理前缀包含/topicAbc，如：registry.enableSimpleBroker("/topicAbc")
    public Greeting greeting(HelloMessage message, StompHeaderAccessor accessor) throws Exception {
        System.err.println(accessor.getSessionId()+"  "+accessor.getUser().getName());
        Thread.sleep(1000); // simulated delay
        System.err.println("参数name: "+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/info")
    @SendToUser(value = "/queue/nofications")
    public Greeting nofications(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("参数name: "+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }


    /**
     * 使用@SubscribeMapping注解，处理client的subscribe请求，被注解的方法将直接返回一个信息给连接的client，不会经过Broker.
     * 常用于进行初始化操作。若是在该方法上使用了@SendTo,则会转发至broker处理。
     * @return
     */
    @SubscribeMapping("/init/{code}")
    public String init(@DestinationVariable("code") String code,@Header("simpSessionId") String simpSessionId,
                       @Headers Map<String,Object> map,StompHeaderAccessor accessor){
        System.err.println("初始化code: "+code+"; simpSessionId : "+simpSessionId+"; location: "+accessor.getNativeHeader("location"));
        return "init";
    }

}
