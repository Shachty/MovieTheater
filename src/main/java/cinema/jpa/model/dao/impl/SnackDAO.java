package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Snack;
import cinema.jpa.model.dao.ISnackDAO;
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
public class SnackDAO implements ISnackDAO {

    private final Log log = LogFactory.getLog(SnackDAO.class);

    @PersistenceContext(name = "thePersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Snack")
                .list();
    }

    @Override
    public Snack save(Snack snack) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(snack);
        log.info("saved snack: "+snack.getId());
        return snack;
    }

    @Override
    public void delete(Snack snack) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(snack);
        log.info("deleted snack: "+snack.getId());
    }

}
