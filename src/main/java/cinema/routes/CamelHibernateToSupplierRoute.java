package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.dto.ItemDTO;
import cinema.processor.EnquiryAggregationStrategyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * route to import the current stock on hand from an xml datei into a route.
 */
@Component
public class CamelHibernateToSupplierRoute extends RouteBuilder {

    @PersistenceContext
    EntityManager theManager;

    @Override
    public void configure() throws Exception {

        from("jpa:cinema.jpa.model.Snack?persistenceUnit=default&consumer.namedQuery=@HQL_GET_UNPROCESSED_ORDERS&consumeDelete=false")
            //.beanRef("orderService", "orderToSupplier")
                .to("log:hibernate")
                .to("mock:supplierOutput");


        //upload Enquiry
        from("file:src/main/resources/Item?noop=true")//from("ftp://user:root@localhost/a")
                .log("got file from Hibernate - Items")
                .unmarshal().json(JsonLibrary.Jackson, ItemDTO.class) // TODO: wird nicht mehr gebrauch, wenn von hibernate
                .aggregate(constant(true), enquiryAggregationStrategyProcessor()).completionTimeout(3000)
                .log("aggregate Items to enquiry - Enquiry")
                .setHeader("CamelFileName", simple("enquiry_${in.header.CamelFileName}.json"))// TODO: wird nicht mehr gebrauch, wenn von hibernate
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)// TODO: wird nicht mehr gebrauch, wenn von hibernate
                .to("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")//.to("ftp://user:root@localhost/offers_2")
                .log("written to ftpServer - Enquiry");
    }
    @Bean
    private AggregationStrategy enquiryAggregationStrategyProcessor() {
        return new EnquiryAggregationStrategyProcessor();
    }
}
