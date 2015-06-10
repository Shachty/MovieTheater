package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.processor.EnquiryAggregationStrategyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * route to import the current stock on hand from an xml datei into a route.
 */
@Component
public class CamelHibernateToSupplierRoute extends RouteBuilder {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @PersistenceContext
    EntityManager theManager;

    @Override
    public void configure() throws Exception {
        this.logger.info("CamelHibernateToSupplierRoute");

        from("jpa:cinema.jpa.model.Snack?persistenceUnit=default&consumer.namedQuery=@HQL_GET_ALL_SNACKS&consumeDelete=true&consumer.delay=45000")
            //.beanRef("orderService", "orderToSupplier")
                .log("load Snacks from hibernate")
                .aggregate(constant(true), enquiryAggregationStrategyProcessor()).completionTimeout(3000)
                .log("aggregate Snacks to enquiry - Enquiry")
                .setHeader("CamelFileName", simple("enquiry_${in.header.CamelFileName}.json"))
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)
                .to("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12");


    }
    @Bean
    private AggregationStrategy enquiryAggregationStrategyProcessor() {
        return new EnquiryAggregationStrategyProcessor();
    }

}
