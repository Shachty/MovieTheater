package cinema.routes;

import cinema.config.FOPConfig;
import cinema.dto.TicketDTO;
import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.model.Screening;
import cinema.model.Ticket;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.fop.FopConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by Daniel on 26.05.2015.
 */
@Component
public class CamelConsumeTicketRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:number")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        int reservationNumber = Integer.parseInt(exchange.getIn().getBody().toString());
                        String query = "{'ticket.customerId:'" + reservationNumber + "}";
                        exchange.getIn().setBody(query);
                        exchange.getProperties().put("query", query);
                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=tickets&operation=findOneByQuery")
                .wireTap("direct:marshalConsumedTicket")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(exchange.getProperties().get("query"));
                    }
                }).to("mongodb:mongoBean?database=workflow&collection=tickets&operation=remove");

        from("direct:marshalConsumedTicket")
                .choice()
                .when(body().isEqualTo(null))
                .to("direct:noPdf").endChoice()
                .otherwise()
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, TicketMongoDTO.class)
                .to("direct:toPdf");

        from("direct:pdf")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketMongoDTO ticketgMongoDTO = (TicketMongoDTO) exchange.getIn().getBody();
                        Ticket ticket = ticketgMongoDTO.getTicket();

                        String pdf = "...~~==Movie Theater Ticket==~~..\n\n\n" +
                                "Movie: " + ticket.getMovieName() + "\n" +
                                "Theaterroom: " + ticket.getTheaterRoom() + "\n" +
                                "At: " + ticket.getTime() + "\n" +
                                "Number of seats: " + ticket.getNumberOfPersons() + "\n" +
                                "Price: " + ticket.getPricePerPerson().multiply(new BigDecimal(ticket.getNumberOfPersons())) + "\n\n" +
                                "Enjoy the movie!";

                        exchange.getIn().setBody(pdf);
                    }
                })
                .to("file:src/main/resources/pdf");


        //read from directory, filter for text files
        from("file:src/main/resources/pdf?noop=true&include=([a-zA-Z]|[0-9])*.(txt)")
                .routeId("textToPdf")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        final String body = exchange.getIn().getBody(String.class);
                        final String fileNameWithoutExtension = FOPConfig.getFileNameWithoutExtension(exchange);
                        final String convertToXSLFOBody = FOPConfig.getFilledXSLFO(body);
                        exchange.getIn().setBody(convertToXSLFOBody);
                        exchange.getIn().setHeader(Exchange.FILE_NAME, fileNameWithoutExtension + ".pdf");
                        exchange.getIn().setHeader(FopConstants.CAMEL_FOP_RENDER + "author", "Shreyas Purohit");
                    }
                })
                .to("fop:application/pdf")
                .to("file:src/main/resources/pdfout");


    }
}
