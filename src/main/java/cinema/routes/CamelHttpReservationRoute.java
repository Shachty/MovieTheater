package cinema.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.springframework.stereotype.Component;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.data.Method;
import org.restlet.data.MediaType;

@Component
public class CamelHttpReservationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8081/camel/test?restletMethods=POST,GET,DELETE,PUT")
                .process(new Processor() {

                    public void process(Exchange exchange) throws Exception {
                        // the Restlet request should be available if neeeded
                        Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);
                        Method method = request.getMethod();

                        // use Restlet API to create the response
                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        response.setStatus(Status.SUCCESS_OK);
                        response.setEntity("<response>Beer is Good</response>", MediaType.TEXT_XML);
                        exchange.getOut().setBody(response);
                    }
                });
    }
}
