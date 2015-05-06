package cinema.service;

/**
 * Created by Daniel on 06.05.2015.
 */

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

    @Async
    public void startCoffee() throws Exception {

        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:tmp/in?noop=true").to("file:tmp/out");
            }});

        camelContext.start();

        Thread.sleep(60 * 5 * 1000);
        camelContext.stop();

    }

}
