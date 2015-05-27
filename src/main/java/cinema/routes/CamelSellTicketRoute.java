package cinema.routes;

import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
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

                        exchange.getProperties().put("ticket",new TicketDTO(ticket));

                        String query = "{'screening.time':'" + time + "','screening.theaterRoom.theaterRoomId':" + theaterRoomId + ",'screening.movie.movieName':'" + moviename + "'}";
                        exchange.getIn().setBody(query);

                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=findOneByQuery").log("found Screening: ${body}")
                .choice()
                .when(body().isEqualTo(null)).log("No screening found.")
                .to("direct:sellTicketInvalid").endChoice()
                .otherwise()
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, ScreeningMongoDTO.class)
                .to("direct:sellTicketValid");

        from("direct:sellTicketValid")
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
                        exchange.getIn().setBody(new TicketMongoDTO(null,ticketDTO.getTicket()));
                    }
                })
                .to("direct:toPdf");

        from("direct:sellTicketInvalid")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {


                        String message = "There is no Screening for this.";

                        exchange.getIn().setBody(message);
                    }
                })
                .process(responseProcessor);
    }
}
