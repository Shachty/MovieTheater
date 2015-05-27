package cinema.processor;

/**
 * Created by Jakob on 25.05.2015.
 */

import cinema.dto.EnquiryDTO;
import cinema.dto.ItemDTO;
import cinema.model.Enquiry;
import cinema.model.Item;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Iterator;
import java.util.List;

public class EnquiryAggregationStrategyProcessor implements AggregationStrategy {

    public EnquiryAggregationStrategyProcessor() {
        super();
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Message newIn = newExchange.getIn();
        ItemDTO itemDTO = (ItemDTO) newIn.getBody();
        Item item = itemDTO.getItem();

        Enquiry enquiry;
        EnquiryDTO enquiryDTO;
        List<Item> list;
        if (oldExchange == null) {
            //TODO: ID für Enquiry besorgen
            enquiry = new Enquiry(1,null);
            enquiry.addItemToOffer(item);
            enquiryDTO = new EnquiryDTO(enquiry);
            newExchange.getIn().setBody(enquiryDTO);
            return newExchange;
        } else {
            Message in = oldExchange.getIn();
            enquiryDTO = in.getBody(EnquiryDTO.class);
            enquiry = enquiryDTO.getEnquiry();

            //Items may not exist twice
            list = enquiry.getItems();
            Iterator <Item> itr = list.iterator();
            boolean alreadyExists = false;
            while(itr.hasNext()) {
                Item i = itr.next();
                if (i.getId() == item.getId()){
                    alreadyExists = true;
                    i.setOrderSnackNumber(item.getOrderSnackNumber() + i.getOrderSnackNumber());
                }
            }
            if (!alreadyExists){
                enquiry.addItemToOffer(item);
            }

            enquiryDTO = new EnquiryDTO(enquiry);
            oldExchange.getIn().setBody(enquiryDTO, EnquiryDTO.class);
            return oldExchange;
        }
    }

}