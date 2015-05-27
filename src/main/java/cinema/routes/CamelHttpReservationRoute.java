package cinema.routes;

import cinema.helper.ReservationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelHttpReservationRoute extends RouteBuilder {

    @Autowired
    ReservationProcessor theProcessor;

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/restlet/do-reservation?restletMethods=POST,DELETE,PUT")
                .process(theProcessor).to("mock:do-reservation");
    }
}
