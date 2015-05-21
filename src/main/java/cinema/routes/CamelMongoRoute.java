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

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        JacksonDataFormat format = new JacksonDataFormat();
        format.setAllowJmsType(true);


        from("file:src/main/resources/tickets?noop=true")
                .log("${body}")
                .wireTap("direct:mail")
                .convertBodyTo(String.class)
                .log("to String")
                .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert").log("written to mongoDB");
                /*.unmarshal(format)
        //from("file://src/main/resources/test?noop=true")
                        .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=s.scheickl@gmx.net&subject=Your reservation"));
        ;*/
    }

/*

    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);
        from("timer://foo?fixedRate=true&delay=0&period=10000")
                .to("http4://localhost/mon")
                        .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert")
                .to("file:tmp/mongoout");
    }
*/


}
