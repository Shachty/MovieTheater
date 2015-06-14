package cinema.processor;

import cinema.dto.TicketDTO;
import cinema.model.Ticket;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ScreeningUpdateProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
        Ticket ticket = ticketDTO.getTicket();

        BasicDBObject increment =
                new BasicDBObject().append("$inc",
                        new BasicDBObject().append("screening.theaterRoom.availableSeats", ticket.getNumberOfPersons()*-1));

        BasicDBObject element = new BasicDBObject().append("screening.time",ticket.getTime())
                .append("screening.movie.movieName",ticket.getMovieName())
                .append("screening.theaterRoom.theaterRoomId", ticket.getTheaterRoom());

        exchange.getIn().setBody(new Object[]{element,increment});

    }
}
