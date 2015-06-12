package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.RandomWaitingTimeProcessor;
import cinema.processor.SupplierCsvCreatorProcessor;
import cinema.processor.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierJsonToCsvRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:supplierCsv")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
                .log("got file from ftpServer - enquiries_3")
                .loop(4).copy()
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .process(new SupplierCsvCreatorProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.csv"))
                .process(new RandomWaitingTimeProcessor())
                .marshal().csv()
                .delay(simple("${in.header.waitingTime}"))
                .log(simple("3 - ${in.header.waitingTime}").getText())
                .recipientList(simple("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_3${property.CamelLoopIndex}?binary=true&password=OmaOpa_12"))//.recipientList(simple("file:src/main/resources/offers/offers_3${property.CamelLoopIndex}"))//.to("ftp://user:root@localhost/offers_3")
                .log(simple("written to ftpServer - offers_3${property.CamelLoopIndex}").getText());

    }
}
