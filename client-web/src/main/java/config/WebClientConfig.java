package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Darren on 2017-12-13
 **/
@Configuration
@EnableWebMvc
public class WebClientConfig extends WebMvcConfigurerAdapter{


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);
        //server项目客户端
        registry.addResourceHandler("/page/*").addResourceLocations("classpath:/html/");

        //stomp broker客户端
        registry.addResourceHandler("/broker/*").addResourceLocations("classpath:/broker/");

        registry.addResourceHandler("/js/*").addResourceLocations("classpath:/js/");
    }
}
