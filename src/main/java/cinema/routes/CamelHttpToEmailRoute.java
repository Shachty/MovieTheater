package cinema.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class CamelHttpToEmailRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:")
                .log("http to email")
                .to("direct:");

    }
}
