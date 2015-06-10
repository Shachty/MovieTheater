package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.dto.TicketDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Ticket;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;


@Component
public class CamelMailRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:mail_GOOD")
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
                .to("direct:mail");


        from("direct:mail_ChooseSupplier")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        OfferDTO offerDTO = (OfferDTO)exchange.getIn().getBody();
                        Offer offer = offerDTO.getOffer();
                        // ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        //Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail", offer.getCompanyMail());

                        String message = "Dear " + offer.getCompanyName() + ",\n" +
                                "We accept your offer of:";

                        Iterator<Item> iterator = offer.getItems().iterator();
                        while (iterator.hasNext()){
                            Item i = iterator.next();
                            message += "\n" + i.getOrderSnackNumber() + "x " + i.getSnack().getName();
                        }
                        message += " \nFor the price of:" + offer.getSumPrice()+  "€" +
                                "\n\nBest regards\nThe Movie Theater";
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
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println();
                    }
                })
                .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=${property[mail]}&subject=Your reservation&contentType=text/html."));

    }

}
