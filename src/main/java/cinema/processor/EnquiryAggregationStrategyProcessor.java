package cinema.processor;

/**
 * Created by Jakob on 25.05.2015.
 */

import cinema.dto.EnquiryDTO;
import cinema.jpa.model.Snack;
import cinema.model.Enquiry;
import cinema.model.Item;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnquiryAggregationStrategyProcessor implements AggregationStrategy {

    private ArrayList<Double> minimalStockList;

    public EnquiryAggregationStrategyProcessor() {
        super();
        minimalStockList = new ArrayList<Double>();
        minimalStockList.add(0,13.0);
        minimalStockList.add(1,10.0);
        minimalStockList.add(2,12.0);
        minimalStockList.add(3,15.0);
        minimalStockList.add(4,15.0);
        minimalStockList.add(5,15.0);
        minimalStockList.add(6,15.0);
        minimalStockList.add(7,15.0);
        minimalStockList.add(8,20.0);
        minimalStockList.add(9,12.0);
        minimalStockList.add(10,25.0);
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

            Message newIn = newExchange.getIn();
            Snack snack = (Snack) newIn.getBody();
        if(minimalStockList.size()+1 >= snack.getId().intValue() && minimalStockList.get(snack.getId().intValue()) < snack.getNumber()) {

            cinema.model.Snack snack1 = new cinema.model.Snack(snack.getId(), snack.getName(), snack.getNumber());
            long orderSnackNumber = (long) (2* minimalStockList.get(snack.getId().intValue()) - snack.getNumber());
            Item item = new Item(snack.getId(), snack.getId().toString(), snack1,orderSnackNumber , 0.0);

            Enquiry enquiry;
            EnquiryDTO enquiryDTO;
            List<Item> list;
            if (oldExchange == null) {
                enquiry = new Enquiry(1, null);
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
                Iterator<Item> itr = list.iterator();
                boolean alreadyExists = false;
                while (itr.hasNext()) {
                    Item i = itr.next();
                    if (i.getId() == item.getId()) {
                        alreadyExists = true;
                        i.setOrderSnackNumber(item.getOrderSnackNumber() + i.getOrderSnackNumber());
                    }
                }
                if (!alreadyExists) {
                    enquiry.addItemToOffer(item);
                }

                enquiryDTO = new EnquiryDTO(enquiry);
                oldExchange.getIn().setBody(enquiryDTO, EnquiryDTO.class);

            }
        }
            return oldExchange;
    }

}