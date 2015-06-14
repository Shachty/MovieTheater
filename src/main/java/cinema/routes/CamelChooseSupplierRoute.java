package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Snack;
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
import java.util.List;
import java.util.logging.Logger;

@Component
public class CamelChooseSupplierRoute extends RouteBuilder {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{OfferDTO.class, Offer.class, Item.class, Item.class, Snack.class});
        DataFormat jaxb = new JaxbDataFormat(jaxbContext);

        this.logger.info("CamelChooseSupplierRoute");

        OfferAggregationStrategyProcessor offerAggregationStrategyProcessor = new OfferAggregationStrategyProcessor();
        
        from("ftp://{{ftp.username}}@{{ftp.hostname}}:21/htdocs/in?binary=true&password={{ftp.password}}&recursive=true&consumer.delay=35000&delete=true")
                .choice()
                .when(header("CamelFileName").endsWith(".csv"))
                .unmarshal().csv()
                .process(new SupplierCsvUnmarshalProcessor())
                .endChoice()
                .when(header("CamelFileName").endsWith(".json"))
                .unmarshal().json(JsonLibrary.Jackson, OfferDTO.class)
                .endChoice()
                .when(header("CamelFileName").endsWith(".xml"))
                .unmarshal(jaxb)
                .end()
                .aggregate(constant(true), new OfferAggregationStrategyProcessor()).completionTimeout(3000)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                        Offer offer = offerDTO.getOffer();
                        List<Item> itemList = offer.getItems();
                        String message = "best offer -> email: " + offer.getCompanyMail() + " price:" + offer.getSumPrice() + ", ITEMS[";

                        for (Item i : itemList) {
                            message += " name: " + i.getSnack().getName() + " order size: " + i.getOrderSnackNumber() + " price: " + i.getPrice() + ";";
                        }
                        logger.info(message + " ]");
                    }
                })
                .to("direct:mail_ChooseSupplier")
                .log("send confirm-notification to supplier");
    }
}
