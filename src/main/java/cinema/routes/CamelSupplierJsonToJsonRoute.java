package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
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
