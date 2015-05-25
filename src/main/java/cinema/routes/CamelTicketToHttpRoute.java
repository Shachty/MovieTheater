package cinema.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 17.05.2015.
 */

@Component
public class CamelTicketToHttpRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);


        from("file:src/main/resources/reservations?noop=true")
                .marshal(xmlJsonFormat)
                .log("xml to json")
                .convertBodyTo(String.class)
                .setHeader(Exchange.HTTP_QUERY, simple("body=${body}"))
                .to("http4://localhost:8080/reserve");

    }
}
