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

        //from("bean:orderService?method=getOrders")
            //.beanRef("orderService", "orderToSupplier")
        //    .to("mock:supplierOutput");
    }

}
