package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Screening;
import cinema.jpa.model.dao.IScreeningDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 */
@Component
public class ScreeningDAO implements IScreeningDAO {

    private final Log log = LogFactory.getLog(ScreeningDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Screening")
                .list();
    }

    @Override
    public Screening save(Screening screening) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(screening);
        log.info("saved screening: "+screening.getId());
        return screening;
    }

    @Override
    public void delete(Screening screening) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(screening);
        log.info("deleted screening: "+screening.getId());
    }

}
