package cinema.processor;


import cinema.service.SnackService;
import cinema.service.TicketReservationService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;

@Component
public class OrderSnackProcessor implements Processor {

    @Autowired
    SnackService snackService;

    @Override
    public void process(Exchange exchange) throws Exception {
        int numberOfSnacks = 10;
        HashMap<String, Integer> order = new HashMap<>();
        for(int i =0;i<numberOfSnacks;i++){
            String snack="snack"+String.valueOf(i+1);
            Object header = exchange.getIn().getHeader(snack);

            if(header!=null){
                order.put(snack, Integer.valueOf(header.toString()));
            } else {
                order.put(snack,0);
            }

        }

        boolean answer = this.snackService.orderSnacks(order);

        // use Restlet API to create the response
        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        if(answer) {
            response.setStatus(Status.SUCCESS_OK);
            response.setEntity("<response><message>Your order has been processed.</message></response>", MediaType.TEXT_XML);
        } else {
            response.setStatus(Status.SUCCESS_OK);
            response.setEntity("<response><message>Your order could not be processed correctly. Order not on stock!</message></response>", MediaType.TEXT_XML);
        }
        exchange.getOut().setBody(response);
    }
}
