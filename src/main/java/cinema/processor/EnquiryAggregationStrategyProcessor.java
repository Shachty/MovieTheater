package cinema.processor;

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
import java.util.logging.Logger;

public class EnquiryAggregationStrategyProcessor implements AggregationStrategy {

    private ArrayList<Double> minimalStockList;
    private final Logger logger = Logger.getLogger(this.getClass().toString());
    private static int count =1;

    public EnquiryAggregationStrategyProcessor() {
        super();
        minimalStockList = new ArrayList<Double>();
        minimalStockList.add(0,0.0);
        minimalStockList.add(1,12.0);
        minimalStockList.add(2,15.0);
        minimalStockList.add(3,8.0);
        minimalStockList.add(4,18.0);
        minimalStockList.add(5,12.0);
        minimalStockList.add(6,15.0);
        minimalStockList.add(7,15.0);
        minimalStockList.add(8,15.0);
        minimalStockList.add(9,12.0);
        minimalStockList.add(10,25.0);
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

            Message newIn = newExchange.getIn();
            Snack snack = (Snack) newIn.getBody();
        logger.info("load snack -> id:" + snack.getId() +" name: " + snack.getName() +" value: " + snack.getNumber() +" min-value: " + minimalStockList.get(snack.getId().intValue()));
        if( minimalStockList.get(snack.getId().intValue()) > snack.getNumber()) {

            cinema.model.Snack snack1 = new cinema.model.Snack(snack.getId(), snack.getName(), snack.getNumber());
            long orderSnackNumber = (long) (2* minimalStockList.get(snack.getId().intValue()) - snack.getNumber());
            Item item = new Item(snack.getId(), snack.getId().toString(), snack1,orderSnackNumber , 0.0);

            Enquiry enquiry;
            EnquiryDTO enquiryDTO;
            List<Item> list;
            if (oldExchange == null) {
                enquiry = new Enquiry(count ++, null);
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