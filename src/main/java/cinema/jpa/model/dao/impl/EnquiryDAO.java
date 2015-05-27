package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Enquiry;
import cinema.jpa.model.dao.IEnquiryDAO;
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
public class EnquiryDAO implements IEnquiryDAO {

    private final Log log = LogFactory.getLog(EnquiryDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Enquiry")
                .list();
    }

    public List findUnprocessed() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("@HQL_GET_UNPROCESSED_ORDERS");
        List<Enquiry> list = query.list();
        return list;
    }

    @Override
    public Enquiry save(Enquiry enquiry) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(enquiry);
        log.info("saved enquiry: "+ enquiry.getId());
        return enquiry;
    }

    @Override
    public void delete(Enquiry enquiry) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(enquiry);
        log.info("deleted enquiry: "+ enquiry.getId());
    }

}
