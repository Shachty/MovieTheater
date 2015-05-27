package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.model.Ticket;
import cinema.processor.ScreeningUpdateProcessor;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
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

        from("direct:persistTicket")
                .to("mongodb:mongoBean?database=workflow&collection=tickets&operation=insert").log("written to mongoDB");

        from("direct:updateScreening")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();

                        BasicDBObject increment =
                                new BasicDBObject().append("$inc",
                                        new BasicDBObject().append("screening.theaterRoom.availableSeats", ticket.getNumberOfPersons()*-1));

                       BasicDBObject element = new BasicDBObject().append("screening.time",ticket.getTime())
                               .append("screening.movie.movieName",ticket.getMovieName())
                               .append("screening.theaterRoom.theaterRoomId", ticket.getTheaterRoom());

                        exchange.getIn().setBody(new Object[]{element,increment});

                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=update")
                .to("direct:socialMedia");
    }


}
