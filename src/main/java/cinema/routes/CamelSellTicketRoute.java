package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.processor.CheckScreeningProcessor;
import cinema.processor.ScreeningUpdateProcessor;
import cinema.processor.ResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.restlet.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 27.05.2015.
 */
@Component
public class CamelSellTicketRoute extends RouteBuilder {

    @Autowired
    ScreeningUpdateProcessor screeningUpdateProcessor;
    @Autowired
    ResponseProcessor responseProcessor;
    @Autowired
    CheckScreeningProcessor checkScreeningProcessor;

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/restlet/sell-ticket?restletMethods=POST,DELETE,PUT").
                process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        String time = exchange.getIn().getHeader("time").toString();
                        int theaterRoomId = Integer.parseInt(exchange.getIn().getHeader("theaterroom").toString());
                        String moviename = exchange.getIn().getHeader("moviename").toString();

                        Ticket ticket = new Ticket(
                                TicketStatus.BUYING_IN_PROGRESS,
                                exchange.getIn().getHeader("first name").toString(),
                                exchange.getIn().getHeader("last name").toString(),
                                Integer.parseInt(exchange.getIn().getHeader("numberofpersons").toString()),
                                theaterRoomId,
                                moviename,
                                time,
                                Long.MAX_VALUE,
                                ""
                        );

                        exchange.getProperties().put("ticket", new TicketDTO(ticket));

                        String query = "{'screening.time':'" + time + "','screening.theaterRoom.theaterRoomId':" + theaterRoomId + ",'screening.movie.movieName':'" + moviename + "'}";
                        exchange.getIn().setBody(query);

                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findOneByQuery").log("found Screening: ${body}")
                .process(checkScreeningProcessor)
                .choice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.GOOD))
                .wireTap("direct:sellTicket_GOOD")
                .wireTap("direct:updateScreening")
                .to("direct:persistTicket").endChoice()
                .when(header("ticketStatus").isEqualTo(TicketStatus.FULL))
                .to("direct:sellTicket_FULL").endChoice()
                .otherwise()
                .to("direct:sellTicket_INVALID");

        from("direct:sellTicket_GOOD")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScreeningMongoDTO screeningMongoDTO = (ScreeningMongoDTO) exchange.getIn().getBody();
                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        ticketDTO.getTicket().setPricePerPerson(screeningMongoDTO.getScreening().getPricePerPerson());
                        exchange.getIn().setBody(ticketDTO);
                    }
                })
                .process(screeningUpdateProcessor)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        exchange.getIn().setBody(new TicketMongoDTO(null, ticketDTO.getTicket()));
                    }
                })
                .to("direct:toPdf");

        from("direct:sellTicket_FULL")
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

        from("direct:sellTicket_INVALID")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketDTO ticketDTO = (TicketDTO) exchange.getProperty("ticket");
                        Ticket ticket = ticketDTO.getTicket();

                        String message = "There is no Screening for the movie: "+ticket.getMovieName() + "\n" +
                                "in theaterroom: " + ticket.getTheaterRoom() + " at " + ticket.getTime() + "!";
                        exchange.getIn().setBody(message);
                    }
                })
                .process(responseProcessor);
    }
}
