package cinema.routes;


import cinema.processor.OrderSnackProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelHttpSnackOrderingRoute extends RouteBuilder {

    @Autowired
    OrderSnackProcessor orderSnackProcessor;

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/restlet/buy-snack?restletMethods=POST,DELETE,PUT")
                .process(orderSnackProcessor);
    }
}

