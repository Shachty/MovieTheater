package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.jpa.model.Screening;
import cinema.model.Ticket;
import cinema.processor.ResponseProcessor;
import cinema.processor.ScreeningUpdateProcessor;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.restlet.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11.05.2015.
 */
@Component
public class CamelMongoRoute extends RouteBuilder {

    @Autowired
    ScreeningUpdateProcessor screeningUpdateProcessor;


    @Override
    public void configure() throws Exception {

        from("seda:persistTicket")
                .removeHeaders("*")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDto = (TicketDTO) exchange.getProperty("ticket");
                        Ticket ticket = ticketDto.getTicket();

                        exchange.getIn().setBody(ticket);
                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=tickets&operation=insert").log("written to mongoDB")
              .process(new Processor() {
                  @Override
                  public void process(Exchange exchange) throws Exception {
                      exchange.getIn().setBody("Check your mail. You'll get a confirmation mail soon.");
                  }
              });


        from("seda:updateScreening")
               .process(screeningUpdateProcessor)
               .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=update")
               .to("direct:socialMedia");

        from("direct:showScreenings")
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findAll")
                .to("direct:responseAllScreenings");

    }



}
