package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.RandomWaitingTimeProcessor;
import cinema.processor.SupplierOfferProcessor;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierJsonToJsonRoute extends RouteBuilder {
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

        from("direct:supplierJson")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
                .loop(4).copy()
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}_2${property.CamelLoopIndex}.json"))
                .process(new RandomWaitingTimeProcessor())
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .delay(simple("${in.header.waitingTime}"))
                .recipientList(simple("ftp://{{ftp.username}}@{{ftp.hostname}}:21/htdocs/in?binary=true&password={{ftp.password}}"))//.recipientList(simple("file://tmp/test/out/offers_2${property.CamelLoopIndex}"))//
                .log("written offer to ftpServer");
    }
}
