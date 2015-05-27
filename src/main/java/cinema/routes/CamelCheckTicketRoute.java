package cinema.routes;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.TicketDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.processor.CheckScreeningProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 25.05.2015.
 */
@Component
public class CamelCheckTicketRoute extends RouteBuilder {

    @Autowired
    CheckScreeningProcessor checkScreeningProcessor;

    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/tickets?noop=true")
                .unmarshal().json(JsonLibrary.Jackson, TicketDTO.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println();
                        TicketDTO ticketDTO = (TicketDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketDTO.getTicket();
                        String moviename = ticket.getMovieName();
                        int theaterRoomId = ticket.getTheaterRoom();
                        String time = ticket.getTime();

                        exchange.getProperties().put("ticket", ticketDTO);

                        String query = "{'screening.time':'" + time + "','screening.theaterRoom.theaterRoomId':" + theaterRoomId + ",'screening.movie.movieName':'" + moviename + "'}";
                        exchange.getIn().setBody(query);
                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findOneByQuery").log("found Screening: ${body}")
                .choice()
                .when(body().isEqualTo(null)).log("No screening found.")
                .to("direct:checkScreening").endChoice()
                .otherwise()
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, ScreeningMongoDTO.class)
                .to("direct:checkScreening");

        from("direct:checkScreening")
                .process(checkScreeningProcessor)
                .choice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.GOOD))
                .wireTap("direct:mail_GOOD")
                .wireTap("direct:updateScreening")
                .to("direct:persistTicket").endChoice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.FULL))
                .to("direct:mail_FULL").endChoice()
                .otherwise()
                .to("direct:mail_INVALID")
        ;

    }
}
