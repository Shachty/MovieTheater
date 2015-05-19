package cinema.service;

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

                from("ftp://localhost/dir")
                        .to("file:tmp/ftp/out");
                //from("file:tmp/in?noop=true")
               //         .to("file:tmp/out")
                        //.to("twitter://timeline/user?consumerKey=4ztAqgtOpu61vyuUiLxqoI2xV&consumerSecret=9uuh7Vm7KtTmlyt2QDADLzYOaX0VkkRSfbtLwf8pPxDMS6THbB&accessToken=3240346329-PdlJTNwUHre4YW5ySic7x1505NaCZCTfC4JCheM&accessTokenSecret=FnLGjfjm9raE5Pyrs35XK5C5xb3recJ6Rg5TtZyLYEI4f")
                //;

            }});

        camelContext.start();

        Thread.sleep(60 * 5 * 1000);
        camelContext.stop();

    }

}
