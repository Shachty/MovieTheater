package cinema.routes;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.TicketDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.processor.CheckScreeningProcessor;
import cinema.processor.ReservationProcessor;
import cinema.service.CustomerIdNumberService;
import com.mongodb.BasicDBObject;
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
public class CamelReserveTicketRoute extends RouteBuilder {

    @Autowired
    ReservationProcessor theProcessor;
    @Autowired
    CustomerIdNumberService customerIdNumberService;
    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/restlet/do-reservation?restletMethods=POST,DELETE,PUT,GET")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        String time = exchange.getIn().getHeader("time").toString();
                        int theaterRoomId = Integer.parseInt(exchange.getIn().getHeader("theaterroom").toString());
                        String moviename = exchange.getIn().getHeader("moviename").toString();

                        Ticket ticket = new Ticket(
                                TicketStatus.RESERVATION_IN_PROGRESS,
                                exchange.getIn().getHeader("first name").toString(),
                                exchange.getIn().getHeader("last name").toString(),
                                Integer.parseInt(exchange.getIn().getHeader("numberofpersons").toString()),
                                theaterRoomId,
                                moviename,
                                time,
                                customerIdNumberService.getNextCustomerNumber(),
                                exchange.getIn().getHeader("e-mail").toString()
                        );

                        exchange.getProperties().put("ticket", new TicketDTO(ticket));

                        BasicDBObject query = new BasicDBObject().append("screening.time",time).append("screening.theaterRoom.theaterRoomId",theaterRoomId).append("screening.movie.movieName",moviename);

                        exchange.getIn().setBody(query);
                        exchange.getIn().getHeaders().put("isReservation",true);

                    }
                })
                .to("direct:ticketChecker");
        /*
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

                        BasicDBObject query = new BasicDBObject().append("screening.time", time).append("screening.theaterRoom.theaterRoomId", theaterRoomId).append("screening.movie.movieName", moviename);


                        exchange.getIn().setBody(query);

                        exchange.getIn().getHeaders().put("isReservation", true);
                    }
                })
                .to("direct:ticketChecker");

*/




    }
}
