package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.model.Ticket;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 11.05.2015.
 */
@Component
public class CamelMongoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/tickets?noop=true")
                .log("${body}")
                .wireTap("direct:mail")
                .convertBodyTo(String.class)
                .log("to String")
                .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert").log("written to mongoDB");

    }

}
