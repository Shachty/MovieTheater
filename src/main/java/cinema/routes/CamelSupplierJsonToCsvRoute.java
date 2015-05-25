package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.helper.SupplierCsvCreatorProcessor;
import cinema.helper.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;


@Component
public class CamelSupplierJsonToCsvRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/enquiries_3?noop=true")//from("ftp://user:root@localhost/a")
                .log("got file from ftpServer - enquiries")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .process(new SupplierCsvCreatorProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.csv"))
                .marshal().csv()
                .to("file:src/main/resources/offers_3")//.to("ftp://user:root@localhost/offers_2")
                .log("written to ftpServer - offers_3");

    }
}
