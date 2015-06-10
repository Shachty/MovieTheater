package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.processor.CheckScreeningProcessor;
import cinema.processor.ResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Daniel on 28.05.2015.
 */
@Component
public class CamelTicketCheckerRoute extends RouteBuilder {

    @Autowired
    CheckScreeningProcessor checkScreeningProcessor;
    @Autowired
    ResponseProcessor responseProcessor;
    @Override
    public void configure() throws Exception {

    //gets the corresponding screening for the given query
        from("direct:ticketChecker")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println();
                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findOneByQuery").log("found Screening: ${body}")
                .choice()
                .when(body().isEqualTo(null))
                .to("direct:checkScreening").endChoice()
                .otherwise()
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, ScreeningMongoDTO.class)
                .to("direct:checkScreening");

        //checks wheter the screening is ready to attend
        from("direct:checkScreening")
                .process(checkScreeningProcessor)
                .choice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.GOOD))
                .wireTap("seda:updateScreening")
                  .choice()
                    .when(header("isReservation").isEqualTo(true))
                     .wireTap("seda:mail_GOOD")
                     .to("seda:persistTicket").endChoice()
                     .when(header("isReservation").isEqualTo(false))
                .to("seda:sellTicket_GOOD").endChoice().endChoice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.FULL))
                .to("seda:screening_FULL").endChoice()
                .otherwise()
                .to("seda:screening_INVALID")
        ;

        from("seda:sellTicket_GOOD")
                .removeHeaders("*")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScreeningMongoDTO screeningMongoDTO = (ScreeningMongoDTO) exchange.getIn().getBody();
                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        ticketDTO.getTicket().setPricePerPerson(screeningMongoDTO.getScreening().getPricePerPerson());
                        exchange.getIn().setBody(ticketDTO);
                        exchange.getIn().setBody(new TicketMongoDTO(null, ticketDTO.getTicket()));

                }})
                .to("seda:toPdf");

        from("seda:screening_FULL")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        Ticket ticket = ticketDTO.getTicket();

                        String message = "The Screening you requested is sold out!";
                        exchange.getIn().setBody(message);
                    }
                })
                .process(responseProcessor);

        from("seda:screening_INVALID")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        Ticket ticket = ticketDTO.getTicket();

                        String message = "There is no Screening for the movie: " + ticket.getMovieName() + "\n" +
                                "in theaterroom: " + ticket.getTheaterRoom() + " at " + ticket.getTime() + "!";
                        exchange.getIn().setBody(message);
                    }
                })
                .process(responseProcessor);
    }
}
