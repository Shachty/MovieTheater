package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Order;
import cinema.jpa.model.dao.IOrderDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 */
@Component
public class OrderDAO implements IOrderDAO {

    private final Log log = LogFactory.getLog(OrderDAO.class);

    @PersistenceContext(name = "thePersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Order")
                .list();
    }

    public List findUnprocessed() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("@HQL_GET_UNPROCESSED_ORDERS");
        List<Order> list = query.list();
        return list;
    }

    @Override
    public Order save(Order order) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(order);
        log.info("saved order: "+order.getId());
        return order;
    }

    @Override
    public void delete(Order order) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(order);
        log.info("deleted order: "+order.getId());
    }

}
