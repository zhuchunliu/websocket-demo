package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Darren on 2017-12-11
 **/
@SpringBootApplication
@ComponentScan({"broker"})
public class BrokerStart {
    public static void main(String[] args) {
        SpringApplication.run(BrokerStart.class);
    }
}
