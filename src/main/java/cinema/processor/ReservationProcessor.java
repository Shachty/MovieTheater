package cinema.processor;

import cinema.service.TicketReservationService;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationProcessor implements Processor {

    @Autowired
    TicketReservationService ticketReservationService;

    @Override
    public void process(Exchange exchange) throws Exception {

        try {
            String firstName = exchange.getIn().getHeader("firstname").toString();
            String lastName = exchange.getIn().getHeader("lastname").toString();
            Integer numberOfPeople = exchange.getIn().getHeader("numberofpersons", Integer.class);
            Integer theaterRoom = exchange.getIn().getHeader("theaterroom", Integer.class);
            String movieName = exchange.getIn().getHeader("moviename").toString();
            String time = exchange.getIn().getHeader("time").toString();
            String eMail = exchange.getIn().getHeader("e-mail").toString();

            this.ticketReservationService.createReservation(
                    firstName,
                    lastName,
                    movieName,
                    eMail,
                    theaterRoom,
                    numberOfPeople,
                    time);

        } catch (Exception e) {
      /*      // use Restlet API to create the response
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
            response.setEntity("<response><message>missing fields</message></response>", MediaType.TEXT_XML);
      */  }
    //    exchange.getOut().setBody(response);
    }
}
