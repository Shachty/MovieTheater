package cinema.service;

import cinema.jpa.model.Enquiry;
import cinema.jpa.model.Item;
import cinema.jpa.model.dao.impl.EnquiryDAO;
import cinema.jpa.model.dao.impl.SnackDAO;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Tyler
 */
@Component
public class OrderService {

    private final Log log = LogFactory.getLog(OrderService.class);

    @Autowired
    SnackDAO theSnackDao;

    @Autowired
    EnquiryDAO theOrderDao;

    public List<Enquiry> getOrders() {
        this.log.info("Returning Orders");
        List<Enquiry> returnVal = theOrderDao.findAll();
        this.log.debug(returnVal);
        return returnVal;


    }

    @Transactional
    public void orderToSupplier(@Body List body, Exchange exchange) {
        ArrayList<Item> items = new ArrayList<>();

        Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();

        Enquiry theEnquiry = new Enquiry();

        items.add(item1);
        items.add(item2);
        items.add(item3);

        theEnquiry.setItems(items);

        theOrderDao.save(theEnquiry);

        Long lastId = theEnquiry.getId();
        theEnquiry.getItems().get(1).getId();

    }
}
