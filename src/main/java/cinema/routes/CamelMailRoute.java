package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.model.Ticket;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CamelMailRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:mail_GOOD")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", ticket.getMail());

                        String message = "Dear " + ticket.getFirstName() + " " + ticket.getLastName() + ",\n" +
                                "Thank you for your reservation at the movie Theater!\n\n" +
                                "Details of your reservation: \n" +
                                "Movie: " + ticket.getMovieName() + "\n" +
                                "Theaterroom: " + ticket.getTheaterRoom() + "\n" +
                                "Number of Tickets: " + ticket.getNumberOfPersons() + "\n" +
                                "Overall price:" + ticket.getPricePerPerson().multiply(new BigDecimal(ticket.getNumberOfPersons())).toString() +
                                " \n\nBest regards\nThe Movie Theater";
                        exchange.getIn().setBody(message);
                    }
                })
                .to("direct:mail");

        from("direct:mail_FULL")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", ticket.getMail());

                        String message = "Dear " + ticket.getFirstName() + " " + ticket.getLastName() + ",\n" +
                                "Thank you for your reservation at the movie Theater!\n\n" +
                                "Unfortunately your chosen screening of the movie " + ticket.getMovieName() + " is sold out.\n" +
                                "Please choose another screening.\n\n" +
                                "Best regards\nThe Movie Theater";
                        exchange.getIn().setBody(message);
                    }
                })
                .to("direct:mail");

        from("direct:mail_INVALID")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", ticket.getMail());

                        String message = "Dear " + ticket.getFirstName() + " " + ticket.getLastName() + ",\n" +
                                "Thank you for your reservation at the movie Theater!\n\n" +
                                "Unfortunately today there is no screening of the movie " + ticket.getMovieName() + "in theaterroom " + ticket.getTheaterRoom() + " at " + ticket.getTime()+ ".\n" +
                                "Please choose another screening.\n\n" +
                                "Best regards\nThe Movie Theater";
                        exchange.getIn().setBody(message);
                    }
                })
                .to("direct:mail");

        from("direct:mail")
                .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=${property[mail]}&subject=Your reservation."));

    }

}
