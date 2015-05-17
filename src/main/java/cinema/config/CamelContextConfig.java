package cinema.config;

import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by Daniel on 17.05.2015.
 */




@Configuration
public class CamelContextConfig {


    //beans for selfregistering
    @Autowired
    Mongo mongoBean;

    @Bean()
    public CamelContext camelContext(){


        //If camelContext messed with the Spring registrated beans, register it yourself using the simpleRegistry
        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mongoBean", mongoBean);

        return new DefaultCamelContext(simpleRegistry);

    }
}
