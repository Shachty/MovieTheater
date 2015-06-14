package cinema.config;

import cinema.service.OrderService;
import cinema.service.SnackService;
import cinema.service.SocialMediaService;
import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CamelContextConfig {


    @Autowired
    Mongo mongoBean;
    @Autowired
    SnackService snackBean;
    @Autowired
    OrderService orderServiceBean;
    @Autowired
    SocialMediaService splitterBean;

    @Bean(name = "camelContext")
    @Scope("singleton")
    public CamelContext camelContext(){

        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mongoBean", mongoBean);
        simpleRegistry.put("snackService", snackBean);
        simpleRegistry.put("orderService", orderServiceBean);
        simpleRegistry.put("splitterBean", splitterBean);

        return new DefaultCamelContext(simpleRegistry);
    }
}
