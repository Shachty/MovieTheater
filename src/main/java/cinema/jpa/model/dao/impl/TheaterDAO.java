package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Theater;
import cinema.jpa.model.dao.ITheaterDAO;
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
public class TheaterDAO implements ITheaterDAO {

    private final Log log = LogFactory.getLog(TheaterDAO.class);

    @PersistenceContext(name = "thePersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Theater")
                .list();
    }

    @Override
    public Theater save(Theater theater) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(theater);
        log.info("saved theater: "+theater.getId());
        return theater;
    }

    @Override
    public void delete(Theater theater) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(theater);
        log.info("deleted theater: "+theater.getId());
    }

}
