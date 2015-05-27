package cinema.helper;

import cinema.TicketReservationService;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationProcessor implements Processor {

    @Autowired
    TicketReservationService ticketReservationService;

    @Override
    public void process(Exchange exchange) throws Exception {
        // the Restlet request should be available if neeeded
        //Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);

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


        // use Restlet API to create the response
        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_OK);
        response.setEntity("<response><message>We received your reservation. You will get a confirmation or declining message on your provided e-mail address soon.</message></response>", MediaType.TEXT_XML);
        exchange.getOut().setBody(response);
    }
}
