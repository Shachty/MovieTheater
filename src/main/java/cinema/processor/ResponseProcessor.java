package cinema.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.restlet.Response;

@Component
public class ResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setEntity("<response><message>"+(String) exchange.getIn().getBody()+"</message></response>", MediaType.TEXT_HTML);
        exchange.getOut().setBody(response);

    }
}
