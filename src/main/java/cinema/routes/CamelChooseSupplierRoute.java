package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.dto.TicketDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Snack;
import cinema.model.Ticket;
import cinema.processor.OfferAggregationStrategyProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import cinema.processor.SupplierCsvUnmarshalProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by Jakob on 08.06.2015.
 */
@Component
public class CamelChooseSupplierRoute extends RouteBuilder {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{OfferDTO.class, Offer.class, Item.class, Item.class, Snack.class});
        DataFormat jaxb = new JaxbDataFormat(jaxbContext);

        this.logger.info("CamelChooseSupplierRoute");

        OfferAggregationStrategyProcessor offerAggregationStrategyProcessor = new OfferAggregationStrategyProcessor();
        
        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in?binary=true&password=OmaOpa_12&recursive=true")
                .log(simple("${in.header.CamelFileName}").getText())
                .choice()
                    .when(header("CamelFileName").endsWith(".csv"))
                        .log("Offer aggregator got .csv-file")
                        .unmarshal().csv()
                        .process(new SupplierCsvUnmarshalProcessor())
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                                Offer offer = offerDTO.getOffer();
                                List<Item> itemList = offer.getItems();
                                String message = "best offer -> id: " + offer.getId() + " price:" + offer.getSumPrice() + ", ITEMS[";

                                for (Item i : itemList) {
                                    message += " name: " + i.getSnack().getName() + " order size: " + i.getOrderSnackNumber() + " price: " + i.getPrice() + ";";
                                }
                                logger.info(message + " ]");
                            }
                        })
                        .log("choose best offer")
                        .to("direct:mail_ChooseSupplier")
                        .log("send confirm-notification to supplier")
                    .endChoice()
                    .when(header("CamelFileName").endsWith(".json"))
                        .log("Offer aggregator got .json-file")
                        .unmarshal().json(JsonLibrary.Jackson, OfferDTO.class)
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                                Offer offer = offerDTO.getOffer();
                                List<Item> itemList = offer.getItems();
                                String message = "best offer -> id: " + offer.getId() + " price:" + offer.getSumPrice() + ", ITEMS[";

                                for (Item i : itemList) {
                                    message += " name: " + i.getSnack().getName() + " order size: " + i.getOrderSnackNumber() + " price: " + i.getPrice() + ";";
                                }
                                logger.info(message + " ]");
                            }
                        })
                        .log("choose best offer")
                        .to("direct:mail_ChooseSupplier")
                        .log("send confirm-notification to supplier")
                    .endChoice()
                    .when(header("CamelFileName").endsWith(".xml"))
                        .log("Offer aggregator got .xml-file")
                        .unmarshal(jaxb)
                        .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                                Offer offer = offerDTO.getOffer();
                                List<Item> itemList = offer.getItems();
                                String message = "best offer -> id: " + offer.getId() + " price:" + offer.getSumPrice() + ", ITEMS[";

                                for (Item i : itemList) {
                                    message += " name: " + i.getSnack().getName() + " order size: " + i.getOrderSnackNumber() + " price: " + i.getPrice() + ";";
                                }
                                logger.info(message + " ]");
                            }
                        })
                        .log("choose best offer")
                        .to("direct:mail_ChooseSupplier")
                        .log("send confirm-notification to supplier")
                    .end();


    }


}
