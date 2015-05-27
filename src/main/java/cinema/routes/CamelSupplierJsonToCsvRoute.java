package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.SupplierCsvCreatorProcessor;
import cinema.processor.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;


@Component
public class CamelSupplierJsonToCsvRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/enquiries_3?noop=true")//from("ftp://user:root@localhost/a")
                .loop(4).copy()
                .log("got file from ftpServer - enquiries_2")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .process(new SupplierCsvCreatorProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.csv"))
                .marshal().csv()
                .recipientList(simple("file:src/main/resources/offers/offers_3${property.CamelLoopIndex}"))//.to("ftp://user:root@localhost/offers_3")
                .log("written to ftpServer - offers_3X");

    }
}
