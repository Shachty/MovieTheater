package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.processor.CheckScreeningProcessor;
import cinema.processor.ScreeningUpdateProcessor;
import cinema.processor.ResponseProcessor;
import cinema.service.CustomerIdNumberService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.restlet.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Daniel on 27.05.2015.
 */
@Component
public class CamelSellTicketRoute extends RouteBuilder {

    @Autowired
    ScreeningUpdateProcessor screeningUpdateProcessor;
    @Autowired
    ResponseProcessor responseProcessor;
    @Autowired
    CheckScreeningProcessor checkScreeningProcessor;
    @Autowired
    CustomerIdNumberService customerIdNumberService;

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/restlet/sell-ticket?restletMethods=POST,DELETE,PUT").
                process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        String time = exchange.getIn().getHeader("time").toString();
                        int theaterRoomId = Integer.parseInt(exchange.getIn().getHeader("theaterroom").toString());
                        String moviename = exchange.getIn().getHeader("moviename").toString();

                        Ticket ticket = new Ticket(
                                TicketStatus.BUYING_IN_PROGRESS,
                                exchange.getIn().getHeader("first name").toString(),
                                exchange.getIn().getHeader("last name").toString(),
                                Integer.parseInt(exchange.getIn().getHeader("numberofpersons").toString()),
                                theaterRoomId,
                                moviename,
                                time,
                                customerIdNumberService.getNextCustomerNumber(),
                                ""
                        );

                        exchange.getProperties().put("ticket", new TicketDTO(ticket));


                        BasicDBObject query = new BasicDBObject().append("screening.time", time).append("screening.theaterRoom.theaterRoomId", theaterRoomId).append("screening.movie.movieName", moviename);

                        //   exchange.getIn().setBody(query);

                        exchange.getIn().setBody(query);
                        exchange.getIn().getHeaders().put("isReservation", false);

                    }
                })
           .to("direct:ticketChecker");
            /*    .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findOneByQuery").log("found Screening: ${body}")
                .process(checkScreeningProcessor)
                .choice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.GOOD))
                .wireTap("direct:sellTicket_GOOD")
                .wireTap("direct:updateScreening")
                .to("direct:persistTicket").endChoice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.FULL))
                .to("direct:sellTicket_FULL").endChoice()
                .otherwise()
                .to("direct:sellTicket_INVALID");
*/

    }
}
