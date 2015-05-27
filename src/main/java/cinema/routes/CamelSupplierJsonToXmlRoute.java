package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.dto.TicketDTO;
import cinema.helper.RandomNumberProcessor;
import cinema.helper.SupplierOfferProcessor;
import cinema.processor.SupplierOfferProcessor;
import cinema.model.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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

        from("direct:supplierXml")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")//from("file:src/main/resources/enquiries?noop=true")
                .log("got file from ftpServer - enquiries")
                .loop(4).copy()
                .unmarshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .process(new SupplierOfferProcessor())
                .setHeader("CamelFileName", simple("offer_${in.header.CamelFileName}.xml"))
                .process(new RandomNumberProcessor())
                .marshal(jaxb)
                .delay(simple("${in.header.waitingTime}"))
                .log(simple("1 - ${in.header.waitingTime}").getText())
                .recipientList(simple("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_1${property.CamelLoopIndex}?binary=true&password=OmaOpa_12"))//.recipientList(simple("file:src/main/resources/offers/offers_1${property.CamelLoopIndex}"))
                .log(simple("written to ftpServer - offers_1${property.CamelLoopIndex}").getText());

    }

}
