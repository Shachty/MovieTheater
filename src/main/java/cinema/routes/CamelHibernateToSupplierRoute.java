package cinema.routes;

import cinema.jpa.model.Order;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jpa.Consumed;
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

        from("jpa:cinema.jpa.model.Order?persistenceUnit=thePersistenceUnit&consumer.namedQuery=@HQL_GET_UNPROCESSED_ORDERS&consumeDelete=false")
            //.beanRef("orderService", "orderToSupplier")
                .to("log:hibernate")
                .to("mock:supplierOutput");
    }

}
