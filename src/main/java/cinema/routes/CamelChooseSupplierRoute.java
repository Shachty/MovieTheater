package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.dto.TicketDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Snack;
import cinema.model.Ticket;
import cinema.processor.OfferAggregationStrategyProcessor;
import cinema.processor.SupplierCsvUnmarshalProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by Jakob on 08.06.2015.
 */
@Component
public class CamelChooseSupplierRoute extends RouteBuilder {



    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{OfferDTO.class, Offer.class, Item.class, Item.class, Snack.class});
        DataFormat jaxb = new JaxbDataFormat(jaxbContext);

        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in?binary=true&password=OmaOpa_12&recursive=true")
                .log(simple("${in.header.CamelFileName}").getText())
                .choice()
                    .when(header("CamelFileName").endsWith(".csv"))
                        .log("Offer aggregator got .csv-file")
                        .unmarshal().csv()
                        .process(new SupplierCsvUnmarshalProcessor())
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .to("direct:mail_ChooseSupplier")
                .endChoice()
                    .when(header("CamelFileName").endsWith(".json"))
                        .log("Offer aggregator got .json-file")
                        .unmarshal().json(JsonLibrary.Jackson, OfferDTO.class)
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .to("direct:mail_ChooseSupplier")
                .endChoice()
                    .when(header("CamelFileName").endsWith(".xml"))
                        .log("Offer aggregator got .xml-file")
                        .unmarshal(jaxb)
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .to("direct:mail_ChooseSupplier")
                .end();


    }


}
