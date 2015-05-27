package cinema.processor;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.model.Enquiry;
import cinema.model.Item;
import cinema.model.Offer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class SupplierOfferProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        EnquiryDTO EnquiryDTO = (EnquiryDTO) exchange.getIn().getBody();
        Enquiry enquiry = EnquiryDTO.getEnquiry();

        Double price = getRandomPrice();

        List<Item> list = new ArrayList<Item>();
        Iterator i = enquiry.getItems().iterator();

        while (i.hasNext()) {
            Item item = (Item) i.next();
            item.setPrice(getRandomPrice());
            list.add(item);
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
