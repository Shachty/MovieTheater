package cinema.routes;

import cinema.dto.OfferDTO;
import cinema.processor.OfferAggregationStrategyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Created by Jakob on 08.06.2015.
 */
public class CamelChooseSupplierRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        OfferAggregationStrategyProcessor offerAggregationStrategyProcessor = new OfferAggregationStrategyProcessor();
        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in/offers_2$binary=true&password=OmaOpa_12")
                .unmarshal().json(JsonLibrary.Jackson, OfferDTO.class)
                .aggregate(constant(true), offerAggregationStrategyProcessor).completionTimeout(3000)
                .to("direct:mail_ChooseSupplier");

    }


}
