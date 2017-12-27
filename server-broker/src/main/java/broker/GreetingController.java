package broker;

import broker.entity.Greeting;
import broker.entity.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Notification;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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
    public void send(){
        template.convertAndSend("/topic/greetings",new Greeting("群发消息"));
        template.convertAndSendToUser("abc","/queue/nofications",new Greeting("群发消息-user"));
    }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("参数name: "+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/info")
    @SendToUser(value = "/queue/nofications",broadcast = false)
    public Greeting nofications(HelloMessage message,Principal principal) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("参数name: "+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }


    /**
     * 使用@SubscribeMapping注解，处理client的subscribe请求，被注解的方法将直接返回一个信息给连接的client，不会经过Broker.
     * 常用于进行初始化操作。若是在该方法上使用了@SendTo,则会转发至broker处理。
     * @return
     */
    @SubscribeMapping("/init")
    public String init(){
        System.err.println("初始化");
        return "init";
    }

}
