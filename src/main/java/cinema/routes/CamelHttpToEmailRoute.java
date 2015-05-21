package cinema.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class CamelHttpToEmailRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:mail")
                .log("${body}")
                .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=daniel.shatkinl@gmail.com&subject=Your reservation"));

    }
}
