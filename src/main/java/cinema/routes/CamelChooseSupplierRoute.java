package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.processor.OfferAggregationStrategyProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jakob on 08.06.2015.
 */
public class CamelChooseSupplierRoute extends RouteBuilder {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    public void configure() throws Exception {

        this.logger.info("CamelChooseSupplierRoute");

        OfferAggregationStrategyProcessor offerAggregationStrategyProcessor = new OfferAggregationStrategyProcessor();
        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_2$binary=true&password=OmaOpa_12&consumer.delay=45000")
                .log("download offer from ftp server")
                .unmarshal().json(JsonLibrary.Jackson, OfferDTO.class)
                .aggregate(constant(true), offerAggregationStrategyProcessor).completionTimeout(3000)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
                        Offer offer = offerDTO.getOffer();
                        List<Item> itemList = offer.getItems();
                        String message = "enquiry -> id: " + offer.getId() + " price:" + offer.getSumPrice()  + ", ITEMS[";

                        for (Item i : itemList) {
                            message += " " + i.getSnack().getName() + " order size: " + i.getOrderSnackNumber() + " price: " + i.getPrice() + ";";
                        }
                        logger.info(message + " ]");
                    }
                })
                .log("choose best offer")
                .to("direct:mail_ChooseSupplier")
                .log("send confirm-notification to supplier");

    }


}
