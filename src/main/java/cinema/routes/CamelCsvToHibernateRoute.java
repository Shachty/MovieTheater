package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.stereotype.Component;

/**
 * route to import the current stock on hand from an xml datei into a route.
 */
@Component
public class CamelCsvToHibernateRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/data/?fileName=stock.csv&noop=true")
                .unmarshal().csv()
                .to("bean:snackService?method=importCsvList");
    }

}
