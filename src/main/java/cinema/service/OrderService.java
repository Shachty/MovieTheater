package cinema.service;

import cinema.jpa.model.Order;
import cinema.jpa.model.Snack;
import cinema.jpa.model.dao.impl.OrderDAO;
import cinema.jpa.model.dao.impl.SnackDAO;
import org.apache.camel.Body;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 */
@Component
public class OrderService {

    private final Log log = LogFactory.getLog(OrderService.class);

    @PersistenceContext
    EntityManager theManager;

    @Autowired
    OrderDAO theOrderDao;

    public List<Order> getOrders() {
        this.log.info("Returning Orders");
        List<Order> returnVal = theOrderDao.findAll();
        this.log.debug(returnVal);
        return returnVal;
    }

    @Transactional
    public void orderToSupplier(@Header("user") String user, @Body List body, Exchange exchange) {
        log.debug("importCsv...");
        log.info(body);


    }
}
