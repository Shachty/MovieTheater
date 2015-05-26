package cinema.routes;

    import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * route to import the current stock on hand from an xml datei into a route.
 */
@Component
public class CamelHibernateToSupplierRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("jpa:cinema.jpa.model.Order?persistenceUnit=thePersistenceUnit&consumer.namedQuery=@HQL_GET_UNPROCESSED_ORDERS&consumeDelete=false")
            //.beanRef("orderService", "orderToSupplier")
                .to("log:hibernate")
                .to("mock:supplierOutput");
    }

}
