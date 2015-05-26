package cinema.jpa.model.dao.impl;

import cinema.jpa.model.Movie;
import cinema.jpa.model.dao.IMovieDAO;
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
public class MovieDAO implements IMovieDAO {

    private final Log log = LogFactory.getLog(MovieDAO.class);

    @PersistenceContext(name = "thePersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List findAll() {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        return session.
        createQuery("from Movie")
                .list();
    }

    @Override
    public Movie save(Movie movie) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.saveOrUpdate(movie);
        log.info("saved movie: "+movie.getId());
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        Session session = this.entityManager.unwrap(org.hibernate.Session.class);
        session.delete(movie);
        log.info("deleted movie: "+movie.getId());
    }

}
