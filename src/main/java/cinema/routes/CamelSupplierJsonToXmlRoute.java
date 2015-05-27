package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.processor.SupplierOfferProcessor;
import cinema.model.*;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

/**
 * Created by Asus on 25.05.2015.
 */

@Component
public class CamelSupplierJsonToXmlRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{OfferDTO.class, Offer.class, PricedItem.class, Item.class, Snack.class});
        DataFormat jaxb = new JaxbDataFormat(jaxbContext);

        from("file:src/main/resources/enquiries?noop=true")//from("ftp://user:root@localhost/a")
                .loop(4).copy()
                .log("got file from ftpServer - enquiries")
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.xml"))
                .marshal(jaxb)
                //.delay(new Random().nextInt(1000 * 10))
                .recipientList(simple("file:src/main/resources/offers/offers_1${property.CamelLoopIndex}"))//.to("ftp://user:root@localhost/offers_1")
                .log("written to ftpServer - offers_1X");

    }

}
