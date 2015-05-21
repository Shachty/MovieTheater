package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.model.Ticket;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class CamelHttpToEmailRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:mail")
                .unmarshal().json(JsonLibrary.Jackson, TicketDTO.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();

                        exchange.getProperties().put("mail",ticket.getMail());

                        String body = "Thank you for your reservation at the movie Theater \n"   +
                                "for the movie" + ticket.getScreening().getMovie().getMovieName()+ "!\n"+
                                "Your Customer Number is: " + ticket.getCustomerId() +      "\n" +
                                "The price for all of your reserved tickets is: " + ticket.getOverallPrice().toString() + ".";
                        exchange.getIn().setBody(body);
                    }
                })
                .log("${property[mail]}")
                .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=${property[mail]}&subject=Your reservation."));

    }
}
