package cinema.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Daniel on 11.05.2015.
 */
public class CamelMongoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("http:http://localhost:8080/mongo")
                       .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert");

        System.out.println("#####wtf####");

    }
}
