package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.RandomWaitingTimeProcessor;
import cinema.processor.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierJsonToJsonRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:supplierJson")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
                .loop(4).copy()
                .log("got file from ftpServer - enquiries_2")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.json"))
                .process(new RandomWaitingTimeProcessor())
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .delay(simple("${in.header.waitingTime}"))
                .recipientList(simple("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_2${property.CamelLoopIndex}?binary=true&password=OmaOpa_12"))//.recipientList(simple("file://tmp/test/out/offers_2${property.CamelLoopIndex}"))//
                .log(simple("written to ftpServer - offers_2${property.CamelLoopIndex}").getText());
    }
}
