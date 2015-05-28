package cinema.processor;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 27.05.2015.
 */
@Component
public class CheckScreeningProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        Screening screening = null;
        try {
            ScreeningMongoDTO screeningMongoDTO = (ScreeningMongoDTO) exchange.getIn().getBody();
            screening = screeningMongoDTO.getScreening();
        } catch (Exception e) {
            System.out.println("No screening found");
        }

        TicketDTO ticketDTO = (TicketDTO) exchange.getProperties().get("ticket");
        Ticket ticket = ticketDTO.getTicket();

        if (screening == null) {
            ticket.setTicketStatus(TicketStatus.INVALID);
        } else if (screening.getTheaterRoom().getAvailableSeats() < ticket.getNumberOfPersons()) {
            ticket.setTicketStatus(TicketStatus.FULL);
        } else {
            ticket.setTicketStatus(TicketStatus.GOOD);
            ticket.setPricePerPerson(screening.getPricePerPerson());

        }

//        exchange.getIn().setBody(ticketDTO);
        exchange.getIn().setHeader("ticketStatus", ticket.getTicketStatus());
    }
}
