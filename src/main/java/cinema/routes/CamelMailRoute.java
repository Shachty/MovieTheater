package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.dto.TicketDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Ticket;
import cinema.processor.ResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.HashMap;


@Component
public class CamelMailRoute extends RouteBuilder {
@Autowired
    ResponseProcessor responseProcessor;


    @Override
    public void configure() throws Exception {

        from("seda:mail_GOOD")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", ticket.getMail());

                        String message = "Dear " + ticket.getFirstName() + " " + ticket.getLastName() + ",\n" +
                                "Thank you for your reservation at the movie Theater!\n\n" +
                                "Details of your reservation: \n" +
                                "Reservationnumber: " + ticket.getCustomerId() + "\n" +
                                "Movie: " + ticket.getMovieName() + "\n" +
                                "Theaterroom: " + ticket.getTheaterRoom() + "\n" +
                                "Number of Tickets: " + ticket.getNumberOfPersons() + "\n" +
                                "Overall price:" + ticket.getPricePerPerson().multiply(new BigDecimal(ticket.getNumberOfPersons())).toString() +
                                " \n\nPlease show your reservationnumber at the cash desk.\n" +
                                "Best regards\nThe Movie Theater";
                        exchange.getIn().setBody(message);
                    }
                })
                .to("seda:mail");


        from("direct:mail_ChooseSupplier")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                        Offer offer = offerDTO.getOffer();
                        // ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        //Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", offer.getCompanyMail());

                        String message = "Dear " + offer.getCompanyName() + ",\n" +
                                "We accept your offer of:";

                        Iterator<Item> iterator = offer.getItems().iterator();
                        while (iterator.hasNext()) {
                            Item i = iterator.next();
                            message += "\n" + i.getOrderSnackNumber() + "x " + i.getSnack().getName();
                        }
                        message += String.format(" \nFor the price of: %.2f" + "€" +
                                "\n\nBest regards\nThe Movie Theater", offer.getSumPrice());
                        exchange.getIn().setBody(message);
                    }
                })
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setHeaders(new HashMap<String, Object>());
                    }
                })
                .recipientList(simple("smtps://smtp.gmail.com?username={{mail.username}}&password={{mail.password}}&to=${property[mail]}&subject=Snack Offer"));


        from("seda:mail")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setHeaders(new HashMap<String, Object>());
                    }
                })
                .recipientList(simple("smtps://smtp.gmail.com?username={{mail.username}}&password={{mail.password}}&to=${property[mail]}&subject=Your reservation"));
    }

}
