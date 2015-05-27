package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.helper.RandomNumberProcessor;
import cinema.helper.SupplierOfferProcessor;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.PricedItem;
import cinema.model.Snack;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.util.Date;
import java.util.Random;

@Component
public class CamelSupplierJsonToJsonRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:supplierJson")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")//from("file:src/main/resources/enquiries_2?noop=true")
                .log("got file from ftpServer - enquiries_2")
                .loop(4).copy()
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.json"))
                .process(new RandomNumberProcessor())
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .delay(simple("${in.header.waitingTime}"))
                .log(simple("2 - ${in.header.waitingTime}").getText())
                .recipientList(simple("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_2${property.CamelLoopIndex}?binary=true&password=OmaOpa_12"))//.recipientList(simple("file:src/main/resources/offers/offers_2${property.CamelLoopIndex}"))//.to("ftp://user:root@localhost/offers_2")
                .log(simple("written to ftpServer - offers_2${property.CamelLoopIndex}").getText());
    }
}
