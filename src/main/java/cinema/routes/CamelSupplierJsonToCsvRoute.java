package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.RandomWaitingTimeProcessor;
import cinema.processor.SupplierCsvCreatorProcessor;
import cinema.processor.SupplierOfferProcessor;
import com.fasterxml.jackson.databind.JsonMappingException;
import facebook4j.FacebookException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
public class CamelSupplierJsonToCsvRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        onException(JsonMappingException.class)
                .process(new Processor() {
                    //empty files
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

                        String logMessage = "";

                        if (caused.getMessage().contains("No content")) {
                            logMessage = "Empty enquiry file";
                        } else {
                            logMessage = caused.getMessage();
                        }

                        exchange.getIn().setBody(logMessage);
                    }
                })
                .log("Handled exception: ${body}")
                .handled(true);

        from("direct:supplierCsv")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
                .loop(4).copy()
                .log("got file from ftpServer - enquiries_3")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .process(new SupplierCsvCreatorProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}_3${property.CamelLoopIndex}.csv"))
                .process(new RandomWaitingTimeProcessor())
                .marshal().csv()
                .delay(simple("${in.header.waitingTime}"))
                .recipientList(simple("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in?binary=true&password=OmaOpa_12"))//.recipientList(simple("file://tmp/test/out/offers_3${property.CamelLoopIndex}"))//
                .log(simple("written to ftpServer - offers_3${property.CamelLoopIndex}").getText());

    }
}
