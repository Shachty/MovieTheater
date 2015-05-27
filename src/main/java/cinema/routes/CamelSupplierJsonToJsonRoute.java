package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierJsonToJsonRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/enquiries_2?noop=true")//from("ftp://user:root@localhost/a")
                .loop(4).copy()
                .log("got file from ftpServer - enquiries_2")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.json"))
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .recipientList(simple("file:src/main/resources/offers/offers_2${property.CamelLoopIndex}"))//.to("ftp://user:root@localhost/offers_2")
                .log("written to ftpServer - offers_2X");
    }
}
