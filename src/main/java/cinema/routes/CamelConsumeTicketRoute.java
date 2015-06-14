package cinema.routes;

import cinema.config.FOPConfig;
import cinema.dto.mongo.TicketMongoDTO;
import cinema.processor.ResponseProcessor;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.fop.FopConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CamelConsumeTicketRoute extends RouteBuilder {

    @Autowired
    ResponseProcessor responseProcessor;

    @Override
    public void configure() throws Exception {

        from("restlet:{{restlet.url}}/consume-reservation?restletMethods=POST,DELETE,PUT,GET")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        int reservationNumber = Integer.parseInt(exchange.getIn().getHeader("CamelHttpQuery").toString());
                        BasicDBObject query = new BasicDBObject().append("customerId", reservationNumber);
                        exchange.getIn().setBody(query);
                        exchange.getProperties().put("query", query);
                    }
                })
                .to("mongodb:mongoBean?database=workflow&collection=tickets&operation=findOneByQuery")
                .wireTap("seda:marshalConsumedTicket")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(exchange.getProperties().get("query"));
                    }
                }).to("mongodb:mongoBean?database=workflow&collection=tickets&operation=remove");

        from("seda:marshalConsumedTicket")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println();
                    }
                })
                .choice()
                .when(body().isEqualTo(null))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody("There is no reservation for this reservationnumber.");
                    }
                }).process(responseProcessor)
                .endChoice()
                .otherwise()
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, TicketMongoDTO.class)
                .to("seda:toPdf");

        from("seda:toPdf")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        TicketMongoDTO ticket = (TicketMongoDTO) exchange.getIn().getBody();

                        String pdf = "...~~==Movie Theater Ticket==~~..\n\n\n" +
                                "Movie: " + ticket.getMovieName() + "\n" +
                                "Theaterroom: " + ticket.getTheaterRoom() + "\n" +
                                "At: " + ticket.getTime() + "\n" +
                                "Number of seats: " + ticket.getNumberOfPersons() + "\n" +
                                "Price: " + ticket.getPricePerPerson().multiply(new BigDecimal(ticket.getNumberOfPersons())) + "\n\n" +
                                "Enjoy the movie!";

                        exchange.getIn().setBody(pdf);
                        exchange.getProperties().put("name", ticket.getFirstName() + " " + ticket.getLastName() + "_" + ticket.getCustomerId());
                    }
                })
                .setHeader("CamelFileName", simple("Ticket_${property[name]}.txt"))
                .to("file:src/main/resources/pdf");

        from("file:src/main/resources/pdf?noop=true")
                .routeId("textToPdf")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        final String body = exchange.getIn().getBody(String.class);
                        final String fileNameWithoutExtension = FOPConfig.getFileNameWithoutExtension(exchange);
                        final String convertToXSLFOBody = FOPConfig.getFilledXSLFO(body);
                        exchange.getIn().setBody(convertToXSLFOBody);
                        exchange.getIn().setHeader(Exchange.FILE_NAME, fileNameWithoutExtension + ".pdf");
                        exchange.getIn().setHeader(FopConstants.CAMEL_FOP_RENDER + "author", "Daniel Shatkin");
                    }
                })
                .to("fop:application/pdf")
                .to("file:src/main/resources/pdfout");

    }
}
