package cinema.processor;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.model.Enquiry;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.PricedItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;


public class SupplierOfferProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        EnquiryDTO EnquiryDTO = (EnquiryDTO) exchange.getIn().getBody();
        Enquiry enquiry = EnquiryDTO.getEnquiry();

        Double price = getRandomPrice();

        List<PricedItem> list = new ArrayList<PricedItem>();
        Iterator i = enquiry.getItems().iterator();

        while (i.hasNext()) {
            list.add(new PricedItem((Item) i.next(), getRandomPrice()));
        }
        Offer offer = new Offer(enquiry.getId(), list);
        exchange.getIn().setBody(new OfferDTO(offer), OfferDTO.class);
        String filename = (String)exchange.getIn().getHeader("CamelFileName");
        exchange.getIn().setHeader("CamelFileName", filename.replace(".json",""));
    }

    private double getRandomPrice(){
        Random random = new Random();
        Double upperLimit = 3.0;
        Double lowerLimit = 1.0;
        return random.nextDouble()*(upperLimit-lowerLimit)+lowerLimit;
    }
}
