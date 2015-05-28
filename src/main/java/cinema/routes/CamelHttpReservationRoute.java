package cinema.routes;

import cinema.processor.ReservationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelHttpReservationRoute extends RouteBuilder {

    @Autowired
    ReservationProcessor theProcessor;

    @Override
    public void configure() throws Exception {
/*

        from("restlet:http://localhost:8081/restlet/do-reservation?restletMethods=POST,DELETE,PUT,GET")
                .process(theProcessor);
    }
*/
    }
}
