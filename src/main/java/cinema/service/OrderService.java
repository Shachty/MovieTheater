package cinema.service;

import cinema.jpa.model.Order;
import cinema.jpa.model.OrderItem;
import cinema.jpa.model.dao.impl.OrderDAO;
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
    OrderDAO theOrderDao;

    public List<Order> getOrders() {
        this.log.info("Returning Orders");
        List<Order> returnVal = theOrderDao.findAll();
        this.log.debug(returnVal);
        return returnVal;


    }

    @Transactional
    public void orderToSupplier(@Body List body, Exchange exchange) {
        ArrayList<OrderItem> items = new ArrayList<>();

        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();
        OrderItem item3 = new OrderItem();

        Order theOrder = new Order();

        items.add(item1);
        items.add(item2);
        items.add(item3);

        theOrder.setItems(items);

        theOrderDao.save(theOrder);

        Long lastId = theOrder.getId();
        theOrder.getItems().get(1).getId();

    }
}
